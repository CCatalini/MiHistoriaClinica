import { Component, OnInit } from '@angular/core';
import { PatientService } from "../../../services/patient/patient.service";
import { Router } from "@angular/router";
import Swal from "sweetalert2";

@Component({
    selector: 'app-all-medics-list',
    templateUrl: './all-medics-list.component.html',
    styleUrls: ['./all-medics-list.component.css']
})
export class AllMedicsListComponent implements OnInit {
    // Datos base
    specialties: string[] = [];
    names: string[] = [];
    
    // Filtros seleccionados
    selectedSpecialty: string = '';
    selectedName: string = '';
    selectedDate: string = '';
    
    // Turnos
    availableTurnos: any[] = [];
    filteredTurnos: any[] = [];
    availableDates: string[] = [];
    
    // Estado de carga
    isLoading: boolean = false;
    
    // Paginación
    currentPage: number = 1;
    itemsPerPage: number = 10;
    totalPages: number = 1;

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        // Verificar usuario
        if (localStorage.getItem('userType') !== 'PATIENT') {
            this.router.navigate(['/patient/login']);
            return;
        }
        
        // Cargar especialidades
            this.userService.getAllSpecialties().subscribe((data: any) => {
                this.specialties = data;
            });
    }

    // ============================================
    // MANEJADORES DE CAMBIO DE FILTROS
    // ============================================

    onSpecialtyChange(): void {
        // Resetear filtros dependientes
        this.selectedName = '';
        this.selectedDate = '';
        this.names = [];
        this.availableTurnos = [];
        this.filteredTurnos = [];
        this.availableDates = [];
        
        if (!this.selectedSpecialty) {
            return;
        }
        
        this.isLoading = true;
        
        // Obtener turnos para la especialidad seleccionada (próximos 30 días)
        const today = new Date().toISOString().split('T')[0];
        
        // Cargar todos los médicos de la especialidad (para el dropdown)
        this.userService.getAllMedicsBySpecialty(this.selectedSpecialty).subscribe(
            (medics: any[]) => {
                this.names = medics.map(m => `${m.name} ${m.lastname}`).sort();
            },
            (error) => {
                console.error('Error al cargar médicos:', error);
            }
        );
        
        // Cargar turnos disponibles
        this.userService.getTurnosBySpecialtyRange(this.selectedSpecialty, today).subscribe(
            (turnosDTO: any[]) => {
                const turnos: any[] = [];
                
                turnosDTO.forEach((dto: any) => {
                    if (dto.availableTurnos && dto.availableTurnos.length > 0) {
                        dto.availableTurnos.forEach((turno: any) => {
                            turnos.push({
                                turnoId: turno.turnoId,
                                medicId: dto.medicId,
                                medicName: dto.medicFullName,
                                specialty: dto.specialty,
                                medicalCenter: dto.medicalCenter,
                                fechaTurno: turno.fecha,
                                horaTurno: turno.hora
                            });
                        });
                    }
                });
                
                this.availableTurnos = turnos;
                this.extractAvailableDates();
                this.filterTurnos();
                this.isLoading = false;
            },
            (error) => {
                console.error('Error al cargar turnos:', error);
                this.isLoading = false;
                this.availableTurnos = [];
                this.filteredTurnos = [];
            }
        );
    }

    onNameChange(): void {
        this.filterTurnos();
    }

    selectDate(date: string): void {
        if (this.selectedDate === date) {
            // Si hace click en la misma fecha, deseleccionar
            this.selectedDate = '';
        } else {
            this.selectedDate = date;
        }
        this.filterTurnos();
    }

    clearDateFilter(): void {
        this.selectedDate = '';
        this.filterTurnos();
    }

    // ============================================
    // LÓGICA DE FILTRADO
    // ============================================

    private extractAvailableDates(): void {
        if (this.availableTurnos.length > 0) {
            const uniqueDates = Array.from(new Set(this.availableTurnos.map((t: any) => t.fechaTurno)));
            this.availableDates = uniqueDates.sort().slice(0, 14); // Máximo 14 días para mostrar
        } else {
            this.availableDates = [];
        }
    }

    private updateMedicNames(): void {
        if (this.availableTurnos.length > 0) {
            const uniqueNames = Array.from(new Set(this.availableTurnos.map((t: any) => t.medicName)));
            this.names = uniqueNames.sort();
        } else {
            this.names = [];
        }
    }

    private filterTurnos(): void {
        this.filteredTurnos = this.availableTurnos.filter(turno => {
            const nameMatch = this.selectedName ? turno.medicName === this.selectedName : true;
            const dateMatch = this.selectedDate ? turno.fechaTurno === this.selectedDate : true;
            return nameMatch && dateMatch;
        });
        
        // Ordenar por hora
        this.filteredTurnos.sort((a, b) => {
            if (a.fechaTurno !== b.fechaTurno) {
                return a.fechaTurno.localeCompare(b.fechaTurno);
            }
            return a.horaTurno.localeCompare(b.horaTurno);
        });
        
        this.calculatePagination();
    }

    // ============================================
    // PAGINACIÓN
    // ============================================

    private calculatePagination(): void {
        this.totalPages = Math.ceil(this.filteredTurnos.length / this.itemsPerPage);
        if (this.currentPage > this.totalPages) {
            this.currentPage = this.totalPages > 0 ? this.totalPages : 1;
        }
    }

    getCurrentPageTurnos(): any[] {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.filteredTurnos.slice(startIndex, endIndex);
    }

    goToPage(page: number): void {
        if (page >= 1 && page <= this.totalPages) {
            this.currentPage = page;
        }
    }

    goToNextPage(): void {
        if (this.currentPage < this.totalPages) {
            this.currentPage++;
        }
    }

    goToPreviousPage(): void {
        if (this.currentPage > 1) {
            this.currentPage--;
        }
    }

    getPageNumbers(): number[] {
        const pages: number[] = [];
        const maxVisiblePages = 5;
        
        if (this.totalPages <= maxVisiblePages) {
            for (let i = 1; i <= this.totalPages; i++) {
                pages.push(i);
            }
        } else {
            let start = Math.max(1, this.currentPage - 2);
            let end = Math.min(this.totalPages, start + maxVisiblePages - 1);
            
            if (end === this.totalPages) {
                start = Math.max(1, end - maxVisiblePages + 1);
            }
            
            for (let i = start; i <= end; i++) {
                pages.push(i);
            }
        }
        
        return pages;
    }

    // ============================================
    // FORMATEO DE FECHAS
    // ============================================

    getDayName(dateStr: string): string {
        const date = new Date(dateStr + 'T12:00:00');
        const days = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];
        return days[date.getDay()];
    }

    getDayNumber(dateStr: string): string {
        const date = new Date(dateStr + 'T12:00:00');
        return date.getDate().toString();
    }

    getMonthName(dateStr: string): string {
        const date = new Date(dateStr + 'T12:00:00');
        const months = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
        return months[date.getMonth()];
    }

    formatSelectedDate(dateStr: string): string {
        const date = new Date(dateStr + 'T12:00:00');
        const options: Intl.DateTimeFormatOptions = { 
            weekday: 'long', 
            day: 'numeric', 
            month: 'long' 
        };
        return date.toLocaleDateString('es-ES', options);
    }

    formatDateShort(dateStr: string): string {
        const date = new Date(dateStr + 'T12:00:00');
        const day = date.getDate();
        const months = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
        const days = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];
        return `${days[date.getDay()]} ${day} ${months[date.getMonth()]}`;
    }

    // ============================================
    // RESERVAR TURNO
    // ============================================

    reserveTurno(turno: any): void {
        if (!turno.turnoId) {
            Swal.fire('Error', 'No se puede reservar este turno. ID no válido.', 'error');
            return;
        }

        const formattedDate = this.formatSelectedDate(turno.fechaTurno);

        Swal.fire({
            title: '¿Confirmar reserva?',
            html: `
                <div style="text-align: left; padding: 10px 0;">
                    <p style="margin: 8px 0;"><strong>👨‍⚕️ Médico:</strong> ${turno.medicName}</p>
                    <p style="margin: 8px 0;"><strong>🏥 Especialidad:</strong> ${turno.specialty}</p>
                    <p style="margin: 8px 0;"><strong>📍 Centro:</strong> ${turno.medicalCenter}</p>
                    <p style="margin: 8px 0;"><strong>📅 Fecha:</strong> ${formattedDate}</p>
                    <p style="margin: 8px 0;"><strong>🕐 Hora:</strong> ${turno.horaTurno}</p>
                </div>
            `,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3fb5eb',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, reservar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.reserveTurno(turno.turnoId).subscribe(
                    () => {
                        Swal.fire({
                            title: '¡Reserva exitosa!',
                            text: 'El turno ha sido reservado correctamente. Recibirás un email de confirmación.',
                            icon: 'success',
                            confirmButtonColor: '#3fb5eb'
                        }).then(() => {
                            // Recargar turnos
                            if (this.selectedSpecialty) {
                                this.onSpecialtyChange();
                            }
                        });
                    },
                    (error: any) => {
                        console.error('Error al reservar turno:', error);
                        let errorMessage = 'No se pudo reservar el turno.';
                        if (error.status === 404) {
                            errorMessage = 'El turno no existe o ya no está disponible.';
                        } else if (error.error && error.error.message) {
                            errorMessage = error.error.message;
                        }
                        Swal.fire('Error', errorMessage, 'error');
                    }
                );
            }
        });
    }
}
