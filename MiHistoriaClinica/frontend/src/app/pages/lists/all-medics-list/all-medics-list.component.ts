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

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Creamos medics list
            this.userService.getAllSpecialties().subscribe((data: any) => {
                this.specialties = data;
            });
        }
    }

    formSubmit() {
        this.userService.getAllMedicsList().subscribe(
            (data: any) => {
                if (Array.isArray(data)) {
                    this.medics = data;
                    this.filteredMedics = data;
                    this.updateSpecialties();
                    this.updateNames();
                    this.getAvailableDates(); // Obtener fechas disponibles
                    this.generateAvailableTurnos(); // Generar turnos disponibles
                } else {
                    Swal.fire('Error', 'La respuesta del servidor no contiene una lista de médicos válida.', 'error');
                }
            },
            (error: any) => {
                if (error.status === 400) {
                    Swal.fire('Error', 'Existen datos erróneos.', 'error');
                } else if (error.status === 404) {
                    Swal.fire('Error', 'No se encontraron pacientes.', 'error');
                } else {
                    Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                }
            }
        );
    }

    generateAvailableTurnos() {
        // Generar turnos disponibles de ejemplo
        // En el futuro, esto vendría del backend
        const medicalCenters = [
            'Hospital Austral',
            'Consultorios Escobar', 
            'Consultorios Champagnat',
            'Hospital Aleman',
            'Hospital Italiano'
        ];
        
        const turnos = [];
        const today = new Date();
        
        // Generar turnos para los próximos 30 días
        for (let i = 1; i <= 30; i++) {
            const date = new Date(today);
            date.setDate(today.getDate() + i);
            
            // Generar 2-3 turnos por día
            const numTurnos = Math.floor(Math.random() * 3) + 2;
            for (let j = 0; j < numTurnos; j++) {
                const medic = this.medics[Math.floor(Math.random() * this.medics.length)];
                if (medic) {
                    const hora = 9 + Math.floor(Math.random() * 8); // Horarios entre 9:00 y 17:00
                    const minuto = Math.floor(Math.random() * 4) * 15; // Intervalos de 15 minutos
                    
                    turnos.push({
                        turnoId: turnos.length + 1,
                        medicId: medic.medicId,
                        medicName: medic.name + ' ' + medic.lastname,
                        specialty: medic.specialty,
                        fechaTurno: date.toISOString().split('T')[0],
                        horaTurno: `${hora.toString().padStart(2, '0')}:${minuto.toString().padStart(2, '0')}`,
                        medicalCenter: medicalCenters[Math.floor(Math.random() * medicalCenters.length)]
                    });
                }
            }
        }
        
        this.availableTurnos = turnos;
        this.filteredTurnos = turnos;
        this.calculatePagination(); // Calcular paginación inicial
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
            const specialtyMatch = this.selectedSpecialty ? turno.specialty === this.selectedSpecialty : true;
            const nameMatch = this.selectedName ? turno.medicName.includes(this.selectedName) : true;
            const dateMatch = this.selectedDateRange.start && this.selectedDateRange.end ? 
                turno.fechaTurno >= this.selectedDateRange.start && turno.fechaTurno <= this.selectedDateRange.end : true;
            
            return specialtyMatch && nameMatch && dateMatch;
        });
        
        // Calcular paginación después de filtrar
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
        // Especialidades presentes en la lista filtrada
        this.specialties = Array.from(new Set(this.medics
            .filter(medic => {
                const nameMatch = this.selectedName ? medic.name === this.selectedName : true;
                return nameMatch;
            })
            .map((m: any) => m.specialty)));
    }

    updateNames() {
        // Nombres presentes en la lista filtrada
        this.names = Array.from(new Set(this.medics
            .filter(medic => {
                const specialtyMatch = this.selectedSpecialty ? medic.specialty === this.selectedSpecialty : true;
                return specialtyMatch;
            })
            .map((m: any) => m.name)));
    }

    getAvailableDates() {
        // Generar fechas de ejemplo para el próximo mes (30 días)
        // En el futuro, esto vendría del backend según los turnos disponibles
        const dates = [];
        const today = new Date();
        for (let i = 1; i <= 30; i++) {
            const date = new Date(today);
            date.setDate(today.getDate() + i);
            dates.push(date.toISOString().split('T')[0]); // Formato YYYY-MM-DD
        }
        this.availableDates = dates;
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
        this.filterMedics();
    }

    onNameChange() {
        this.filterMedics();
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

    redirectToAddTurno(medicId: string) {
        this.router.navigate(['/patient/add-turno'], { queryParams: { medicId: medicId } });
    }
}
