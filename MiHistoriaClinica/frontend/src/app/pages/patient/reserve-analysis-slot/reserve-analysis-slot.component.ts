import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../../../services/patient/patient.service';
import Swal from 'sweetalert2';

@Component({
    selector: 'app-reserve-analysis-slot',
    templateUrl: './reserve-analysis-slot.component.html',
    styleUrls: ['./reserve-analysis-slot.component.css']
})
export class ReserveAnalysisSlotComponent implements OnInit {
    
    // Datos del estudio pendiente (si viene de un análisis asignado)
    analysisId: number | null = null;
    analysisName: string = '';      // Nombre legible para mostrar
    analysisEnum: string = '';      // Enum para llamadas a API
    
    // Filtros
    selectedAnalysisType: string = '';  // Enum seleccionado
    selectedMedicalCenter: string = '';
    selectedDate: string = '';
    
    // Datos
    analysisTypes: string[] = [];
    medicalCenters: string[] = [];
    availableDates: string[] = [];
    availableSlots: any[] = [];
    
    // Estados
    isLoading: boolean = false;
    showCalendar: boolean = false;
    
    // Para el mini-calendario
    currentMonth: Date = new Date();
    calendarDays: any[] = [];

    constructor(
        private patientService: PatientService,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'PATIENT') {
            window.location.href = '/patient/login';
            return;
        }

        // Primero capturar los query params síncronamente
        const params = this.route.snapshot.queryParams;
        if (params['analysisId']) {
            this.analysisId = +params['analysisId'];
        }
        if (params['analysisType']) {
            this.analysisName = params['analysisType'];  // Nombre legible
        }
        if (params['analysisEnum']) {
            this.analysisEnum = params['analysisEnum'];  // Enum para API
            this.selectedAnalysisType = params['analysisEnum'];
        }

        this.loadInitialData();
    }

    loadInitialData(): void {
        // Cargar tipos de estudios
        this.patientService.getAllAnalysisTypes().subscribe(
            (types) => {
                this.analysisTypes = types;
                console.log('Tipos cargados:', types);
                console.log('Tipo seleccionado:', this.selectedAnalysisType);
            },
            (error) => console.error('Error cargando tipos de estudios:', error)
        );

        // Cargar centros médicos
        this.patientService.getAllMedicalCenterNames().subscribe(
            (centers) => {
                this.medicalCenters = centers;
                console.log('Centros médicos cargados:', centers);
            },
            (error) => console.error('Error cargando centros médicos:', error)
        );
    }

    onAnalysisTypeChange(): void {
        this.selectedDate = '';
        this.availableSlots = [];
        this.showCalendar = false;
        // Resetear centro médico cuando cambia el tipo
        this.selectedMedicalCenter = '';
    }

    onMedicalCenterChange(): void {
        this.selectedDate = '';
        this.availableSlots = [];
        // Cargar calendario cuando se selecciona un centro médico
        if (this.selectedAnalysisType && this.selectedMedicalCenter) {
            this.loadAvailableDates();
        } else {
            this.showCalendar = false;
        }
    }

    loadAvailableDates(): void {
        this.isLoading = true;
        this.patientService.getAvailableDatesForAnalysis(
            this.selectedAnalysisType,
            this.selectedMedicalCenter || undefined
        ).subscribe(
            (dates) => {
                this.availableDates = dates;
                this.showCalendar = true;
                this.generateCalendar();
                this.isLoading = false;
            },
            (error) => {
                console.error('Error cargando fechas:', error);
                this.isLoading = false;
            }
        );
    }

    generateCalendar(): void {
        const year = this.currentMonth.getFullYear();
        const month = this.currentMonth.getMonth();
        
        const firstDay = new Date(year, month, 1);
        const lastDay = new Date(year, month + 1, 0);
        
        this.calendarDays = [];
        
        // Días vacíos al inicio
        const startDay = firstDay.getDay();
        for (let i = 0; i < startDay; i++) {
            this.calendarDays.push({ day: null, available: false, date: null });
        }
        
        // Días del mes
        for (let day = 1; day <= lastDay.getDate(); day++) {
            const date = new Date(year, month, day);
            const dateStr = this.formatDateForComparison(date);
            const isAvailable = this.availableDates.includes(dateStr);
            const isPast = date < new Date(new Date().setHours(0, 0, 0, 0));
            
            this.calendarDays.push({
                day: day,
                available: isAvailable && !isPast,
                date: dateStr,
                isPast: isPast
            });
        }
    }

    formatDateForComparison(date: Date): string {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    previousMonth(): void {
        this.currentMonth = new Date(
            this.currentMonth.getFullYear(),
            this.currentMonth.getMonth() - 1,
            1
        );
        this.generateCalendar();
    }

    nextMonth(): void {
        this.currentMonth = new Date(
            this.currentMonth.getFullYear(),
            this.currentMonth.getMonth() + 1,
            1
        );
        this.generateCalendar();
    }

    selectDate(dateStr: string): void {
        this.selectedDate = dateStr;
        this.loadAvailableSlots();
    }

    loadAvailableSlots(): void {
        if (!this.selectedAnalysisType || !this.selectedDate || !this.selectedMedicalCenter) {
            this.availableSlots = [];
            return;
        }

        this.isLoading = true;
        this.patientService.getAvailableSchedules(
            this.selectedAnalysisType,
            this.selectedMedicalCenter,
            this.selectedDate
        ).subscribe(
            (slots) => {
                this.availableSlots = slots;
                this.isLoading = false;
            },
            (error) => {
                console.error('Error cargando turnos:', error);
                this.isLoading = false;
                this.availableSlots = [];
            }
        );
    }

    reserveSlot(slot: any): void {
        if (!this.analysisId) {
            Swal.fire('Error', 'Debes seleccionar un estudio pendiente para reservar turno.', 'warning');
            return;
        }

        const centerName = this.getMedicalCenterDisplayName(slot.medicalCenter);
        
        Swal.fire({
            title: 'Confirmar reserva',
            html: `
                <p><strong>Estudio:</strong> ${this.analysisName}</p>
                <p><strong>Centro:</strong> ${centerName}</p>
                <p><strong>Fecha:</strong> ${this.formatDateDisplay(slot.fecha)}</p>
                <p><strong>Hora:</strong> ${slot.hora}</p>
            `,
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Confirmar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#3fb5eb'
        }).then((result) => {
            if (result.isConfirmed) {
                this.confirmReservation(slot.id);
            }
        });
    }

    confirmReservation(scheduleId: number): void {
        if (!this.analysisId) return;
        
        this.isLoading = true;
        
        this.patientService.reserveStudySchedule(scheduleId, this.analysisId).subscribe(
            (response) => {
                this.isLoading = false;
                Swal.fire({
                    title: 'Turno reservado',
                    text: 'Tu turno para el estudio ha sido reservado exitosamente. Recibirás un recordatorio 24 horas antes.',
                    icon: 'success',
                    confirmButtonColor: '#3fb5eb'
                }).then(() => {
                    this.router.navigate(['/patient/analysisList']);
                });
            },
            (error) => {
                this.isLoading = false;
                console.error('Error reservando turno:', error);
                Swal.fire('Error', 'No se pudo reservar el turno. Por favor, intenta nuevamente.', 'error');
            }
        );
    }

    getMedicalCenterDisplayName(enumName: string): string {
        const names: {[key: string]: string} = {
            'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL': 'Sede Principal - Hospital Universitario Austral',
            'CENTRO_ESPECIALIDAD_OFFICIA': 'Centro de especialidad Officia',
            'CENTRO_ESPECIALIDAD_CHAMPAGNAT': 'Centro de especialidad Champagnat',
            'CENTRO_ESPECIALIDAD_LUJAN': 'Centro de especialidad Lujan'
        };
        return names[enumName] || enumName;
    }

    formatDateDisplay(dateStr: string): string {
        const date = new Date(dateStr + 'T00:00:00');
        const options: Intl.DateTimeFormatOptions = {
            weekday: 'long',
            day: 'numeric',
            month: 'long',
            year: 'numeric'
        };
        return date.toLocaleDateString('es-ES', options);
    }

    getMonthName(): string {
        const options: Intl.DateTimeFormatOptions = { month: 'long', year: 'numeric' };
        return this.currentMonth.toLocaleDateString('es-ES', options);
    }

    formatAnalysisName(enumName: string): string {
        const names: {[key: string]: string} = {
            'HEMOGRAMA_COMPLETO': 'Hemograma Completo',
            'PERFIL_LIPIDICO': 'Perfil Lipídico',
            'GLUCEMIA_EN_AYUNAS': 'Glucemia en Ayunas',
            'PRUEBA_DE_EMABARAZO': 'Prueba de Embarazo',
            'ANALISIS_DE_ORINA': 'Análisis de Orina',
            'ELECTROCARDIOGRAMA': 'Electrocardiograma',
            'RADIOGRAFIA_DE_TORAX': 'Radiografía de Tórax',
            'ECOGRAFIA_ABDOMINAL': 'Ecografía Abdominal',
            'DENSITOMETRIA_OSEA': 'Densitometría Ósea',
            'ANALISIS_DE_TIROIDES': 'Análisis de Tiroides',
            'TOMOGRAFIA_COMPUTARIZADA': 'Tomografía Computarizada',
            'RESONANCIA_MAGNETICA': 'Resonancia Magnética',
            'COLONOSCOPIA': 'Colonoscopía',
            'MAMOGRAFIA': 'Mamografía',
            'TEST_DE_VIH': 'Test de VIH',
            'ANALISIS_DE_HEPATITIS': 'Análisis de Hepatitis',
            'HOLTER_CARDIACO': 'Holter Cardíaco',
            'ANALISIS_DE_ALERGIAS': 'Análisis de Alergias',
            'PAPANICOLAOU': 'Papanicolau',
            'TEST_DE_GLUCOSA_POSPRANDIAL': 'Test de Glucosa Posprandial',
            'CITOLOGIA_DE_ESPUTO': 'Citología de Esputo'
        };
        return names[enumName] || enumName;
    }

    goBack(): void {
        this.router.navigate(['/patient/analysisList']);
    }
}

