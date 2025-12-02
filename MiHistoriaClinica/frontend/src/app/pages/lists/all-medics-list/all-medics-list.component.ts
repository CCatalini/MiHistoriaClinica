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
    medics: any[] = [];
    filteredMedics: any[] = [];
    specialties: string[] = [];
    selectedSpecialty: string = '';
    selectedName: string = '';
    names: string[] = [];
    availableDates: string[] = []; // Fechas disponibles de turnos
    showDateRangeModal: boolean = false;
    startDate: string = '';
    endDate: string = '';
    today: string = new Date().toISOString().split('T')[0]; // Fecha actual en formato YYYY-MM-DD
    selectedDateRange: { start: string; end: string } = { start: '', end: '' };
    
    // Nuevas propiedades para turnos disponibles
    availableTurnos: any[] = [];
    filteredTurnos: any[] = [];
    
    // Propiedades para paginación
    currentPage: number = 1;
    itemsPerPage: number = 10;
    totalPages: number = 1;

    medicalCenters: string[] = [
        'Hospital Austral',
        'Consultorios Escobar',
        'Consultorios Champagnat',
        'Hospital Aleman',
        'Hospital Italiano'
    ];
    selectedMedicalCenter: string = '';

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.userService.getAllSpecialties().subscribe((data: any) => {
                this.specialties = data;
            });
            this.userService.getAllMedicsList().subscribe((data: any) => {
                if (Array.isArray(data)) {
                    this.medics = data;
                    this.filteredMedics = data;
                }
            });
            // Cargar centros médicos usando el mismo endpoint que el calendario del médico
            this.userService.getAllMedicalCenterNames().subscribe((centers: string[]) => {
                this.medicalCenters = centers;
            });
            // Inicializar turnos vacíos - el usuario debe seleccionar una especialidad
            this.availableTurnos = [];
            this.filteredTurnos = [];
            this.totalPages = 1;
        }
    }

    formSubmit() {
        this.userService.getAllMedicsList().subscribe(
            (data: any) => {
                this.medics = Array.isArray(data) ? data : [];
                this.filteredMedics = this.medics;
                this.updateSpecialties();
            },
            (error: any) => {
                console.log(error);
                // Solo mostrar error si es un error real del servidor (500+)
                if (error.status >= 500) {
                    Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                }
                this.medics = [];
                this.filteredMedics = [];
            }
        );
    }

    filterMedics() {
        this.filteredMedics = this.medics.filter(medic => {
            const specialtyMatch = this.selectedSpecialty ? medic.specialty === this.selectedSpecialty : true;
            const nameMatch = this.selectedName ? medic.name === this.selectedName : true;
            return specialtyMatch && nameMatch;
        });
        this.updateSpecialties();
        this.updateNames();
        this.getAvailableDates(); // Actualizar fechas disponibles al filtrar
        this.filterTurnos(); // Filtrar turnos según los criterios
    }

    filterTurnos() {
        this.filteredTurnos = this.availableTurnos.filter(turno => {
            const nameMatch = this.selectedName ? turno.medicName === this.selectedName : true;
            const centerMatch = this.selectedMedicalCenter ? turno.medicalCenter === this.selectedMedicalCenter : true;
            const dateMatch = this.selectedDateRange.start && this.selectedDateRange.end ? 
                turno.fechaTurno >= this.selectedDateRange.start && turno.fechaTurno <= this.selectedDateRange.end : true;
            return nameMatch && centerMatch && dateMatch;
        });
        this.calculatePagination();
    }

    calculatePagination() {
        this.totalPages = Math.ceil(this.filteredTurnos.length / this.itemsPerPage);
        // Asegurar que la página actual sea válida
        if (this.currentPage > this.totalPages) {
            this.currentPage = this.totalPages > 0 ? this.totalPages : 1;
        }
    }

    getCurrentPageTurnos(): any[] {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.filteredTurnos.slice(startIndex, endIndex);
    }

    goToPage(page: number) {
        if (page >= 1 && page <= this.totalPages) {
            this.currentPage = page;
        }
    }

    goToNextPage() {
        if (this.currentPage < this.totalPages) {
            this.currentPage++;
        }
    }

    goToPreviousPage() {
        if (this.currentPage > 1) {
            this.currentPage--;
        }
    }

    getPageNumbers(): number[] {
        const pages: number[] = [];
        const maxVisiblePages = 5;
        
        if (this.totalPages <= maxVisiblePages) {
            // Mostrar todas las páginas si hay 5 o menos
            for (let i = 1; i <= this.totalPages; i++) {
                pages.push(i);
            }
        } else {
            // Mostrar páginas alrededor de la página actual
            let start = Math.max(1, this.currentPage - 2);
            let end = Math.min(this.totalPages, start + maxVisiblePages - 1);
            
            // Ajustar el inicio si estamos cerca del final
            if (end === this.totalPages) {
                start = Math.max(1, end - maxVisiblePages + 1);
            }
            
            for (let i = start; i <= end; i++) {
                pages.push(i);
            }
        }
        
        return pages;
    }

    updateSpecialties() {
        // Mostrar siempre todas las especialidades que envía el backend
        this.userService.getAllSpecialties().subscribe((data: any) => {
            this.specialties = data;
        });
    }

    updateNames() {
        if (this.selectedSpecialty) {
            const today = new Date().toISOString().split('T')[0];
            this.userService.getMedicsWithAvailableTurnosBySpecialty(this.selectedSpecialty, today).subscribe((medics: string[]) => {
                this.names = medics;
                console.log('Nombres de médicos en filtro (desde backend):', this.names);
            });
        } else {
            // Si no hay especialidad seleccionada, limpiar nombres
            this.names = [];
        }
    }

    getAvailableDates() {
        // Extraer fechas únicas de los turnos disponibles reales
        if (this.availableTurnos.length > 0) {
            const uniqueDates = Array.from(new Set(this.availableTurnos.map((t: any) => t.fechaTurno)));
            this.availableDates = uniqueDates.sort();
        } else {
            this.availableDates = [];
        }
        this.filterAvailableDatesByRange();
    }

    filterAvailableDatesByRange() {
        // Si hay un rango seleccionado, filtrar las fechas disponibles
        if (this.selectedDateRange.start && this.selectedDateRange.end) {
            const allDates = [];
            const today = new Date();
            for (let i = 1; i <= 30; i++) {
                const date = new Date(today);
                date.setDate(today.getDate() + i);
                allDates.push(date.toISOString().split('T')[0]);
            }
            
            this.availableDates = allDates.filter(date => {
                return date >= this.selectedDateRange.start && date <= this.selectedDateRange.end;
            });
        } else {
            // Si no hay rango seleccionado, mostrar todas las fechas (próxima semana)
            const dates = [];
            const today = new Date();
            for (let i = 1; i <= 7; i++) {
                const date = new Date(today);
                date.setDate(today.getDate() + i);
                dates.push(date.toISOString().split('T')[0]);
            }
            this.availableDates = dates;
        }
    }

    applyDateRange() {
        if (!this.startDate || !this.endDate) {
            Swal.fire('Error', 'Por favor selecciona ambas fechas.', 'warning');
            return;
        }

        if (this.startDate < this.today) {
            Swal.fire('Error', 'La fecha de inicio no puede ser anterior a hoy.', 'warning');
            return;
        }

        if (this.endDate < this.startDate) {
            Swal.fire('Error', 'La fecha de fin debe ser posterior a la fecha de inicio.', 'warning');
            return;
        }

        // Guardar el rango seleccionado
        this.selectedDateRange = {
            start: this.startDate,
            end: this.endDate
        };

        // Filtrar las fechas disponibles según el rango seleccionado
        this.filterAvailableDatesByRange();
        // Filtrar los turnos según el rango seleccionado
        this.filterTurnos();

        console.log('Rango de fechas:', this.startDate, 'a', this.endDate);
        this.showDateRangeModal = false;
        
        // Limpiar los campos del modal
        this.startDate = '';
        this.endDate = '';
    }

    clearDateRange() {
        this.selectedDateRange = { start: '', end: '' };
        // Restaurar todas las fechas disponibles (próxima semana)
        this.filterAvailableDatesByRange();
        // Restaurar todos los turnos
        this.filterTurnos();
    }

    clearDateFilter() {
        // Limpiar solo el filtro de fecha específica (no el rango)
        this.selectedDateRange = { start: '', end: '' };
        // Restaurar todos los turnos
        this.filterTurnos();
        
        Swal.fire({
            title: 'Filtro limpiado',
            text: 'Mostrando todos los turnos disponibles',
            icon: 'success',
            timer: 1500,
            showConfirmButton: false
        });
    }

    onSpecialtyChange() {
        this.selectedName = '';
        this.filteredMedics = this.medics.filter(medic => this.selectedSpecialty ? medic.specialty === this.selectedSpecialty : true);
        if (this.selectedSpecialty) {
            // Pedir turnos al backend por especialidad y rango de 30 días desde hoy
            const today = new Date().toISOString().split('T')[0];
            this.userService.getTurnosBySpecialtyRange(this.selectedSpecialty, today).subscribe((turnosDTO: any[]) => {
                const turnos: any[] = [];
                turnosDTO.forEach((dto: any) => {
                    if (dto.availableTurnos && dto.availableTurnos.length > 0) {
                        dto.availableTurnos.forEach((turno: any) => {
                            turnos.push({
                                turnoId: turno.turnoId, // ID del turno para reservar
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
                this.getAvailableDates(); // Actualizar fechas disponibles
                this.updateNames(); // Actualizar filtro de médicos
                this.filterTurnos();
            });
        } else {
            this.availableTurnos = [];
            this.filteredTurnos = [];
            this.totalPages = 1;
            this.availableDates = [];
            this.updateNames();
        }
    }

    onNameChange() {
        this.filterTurnos();
    }

    onMedicalCenterChange() {
        this.filterTurnos();
    }

    selectDate(date: string) {
        // Filtrar turnos por la fecha seleccionada
        this.selectedDateRange = {
            start: date,
            end: date
        };
        
        // Aplicar el filtro de fecha
        this.filterTurnos();
        
        // Mostrar mensaje de confirmación
        const formattedDate = new Date(date).toLocaleDateString('es-ES', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        
        Swal.fire({
            title: 'Fecha seleccionada',
            text: `Mostrando turnos disponibles para el ${formattedDate}`,
            icon: 'info',
            timer: 2000,
            showConfirmButton: false
        });
    }

    reserveTurno(turno: any) {
        // Validar que el turno tenga ID
        if (!turno.turnoId) {
            Swal.fire('Error', 'No se puede reservar este turno. ID no válido.', 'error');
            return;
        }

        // Mostrar confirmación con los detalles del turno
        const formattedDate = new Date(turno.fechaTurno).toLocaleDateString('es-ES', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });

        Swal.fire({
            title: '¿Confirmar reserva?',
            html: `
                <div style="text-align: left;">
                    <p><strong>Médico:</strong> ${turno.medicName}</p>
                    <p><strong>Especialidad:</strong> ${turno.specialty}</p>
                    <p><strong>Centro Médico:</strong> ${turno.medicalCenter}</p>
                    <p><strong>Fecha:</strong> ${formattedDate}</p>
                    <p><strong>Hora:</strong> ${turno.horaTurno}</p>
                </div>
            `,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3fb5eb',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, reservar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                // Llamar al servicio para reservar
                this.userService.reserveTurno(turno.turnoId).subscribe(
                    () => {
                        Swal.fire({
                            title: '¡Reserva exitosa!',
                            text: 'El turno ha sido reservado correctamente.',
                            icon: 'success',
                            confirmButtonColor: '#3fb5eb'
                        }).then(() => {
                            // Recargar los turnos para actualizar la lista
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
