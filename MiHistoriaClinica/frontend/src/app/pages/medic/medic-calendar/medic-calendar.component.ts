import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {CalendarOptions} from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import esLocale from '@fullcalendar/core/locales/es';
import { MedicService } from '../../../services/medic/medic.service';
import { forkJoin } from 'rxjs';
import { FullCalendarComponent } from '@fullcalendar/angular';

@Component({
  selector: 'app-medic-calendar',
  templateUrl: './medic-calendar.component.html',
  styleUrls: ['./medic-calendar.component.css']
})

export class MedicCalendarComponent implements OnInit, AfterViewInit {

    @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

    my_events : any = [];
    allTurnos: any[] = [];
    selectedTurnos: any[] = [];

    calendarOptions: CalendarOptions = {
        initialView: 'dayGridMonth',
        events: [],
        eventClick: this.handleEventClick.bind(this),
        dateClick: this.handleDateClick.bind(this),
        locale: esLocale,
        plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        height: 'auto',
        eventDisplay: 'block',
        dayMaxEventRows: 3,
        moreLinkClick: 'day'
    };

    // Variables para el modal de agregar turnos
    showModal: boolean = false;
    daysOfWeek = [
        { name: 'Lunes', value: '1' },
        { name: 'Martes', value: '2' },
        { name: 'Miércoles', value: '3' },
        { name: 'Jueves', value: '4' },
        { name: 'Viernes', value: '5' },
        { name: 'Sábado', value: '6' },
        { name: 'Domingo', value: '0' }
    ];
    availableDays: string[] = [];
    startTime: string = '';
    endTime: string = '';
    slotDuration: number = 30;
    medicalCenter: string = '';
    medicalCenterOptions: string[] = [];

    // Variables para el modal de vista detallada
    showDetailModal: boolean = false;
    selectedDate: string = '';
    selectedDateTurnos: any[] = [];
    viewType: 'all' | 'available' | 'reserved' = 'all';

    // Mapeo de nombre legible a nombre de enum para MedicalCenterE
    medicalCenterEnumMap: {[key: string]: string} = {
      "Sede Principal - Hospital Universitario Austral": "SEDE_PRINCIPAL_HOSPITAL_AUSTRAL",
      "Centro de especialidad Officia": "CENTRO_ESPECIALIDAD_OFFICIA",
      "Centro de especialidad Champagnat": "CENTRO_ESPECIALIDAD_CHAMPAGNAT",
      "Centro de especialidad Lujan": "CENTRO_ESPECIALIDAD_LUJAN"
    };

    // Opciones fijas para duración
    durationOptions: number[] = [15, 30, 45, 60];

    constructor(private medicService: MedicService) {}

    ngOnInit(): void {
        console.log('=== INICIANDO ngOnInit ===');
        
        // Debug del localStorage
        console.log('localStorage completo:', {
            userType: localStorage.getItem('userType'),
            userId: localStorage.getItem('userId'),
            token: localStorage.getItem('token') ? 'Token presente' : 'Token ausente'
        });
        
        if (localStorage.getItem('userType') != 'MEDIC') {
            console.log('Usuario no es MEDIC, redirigiendo...');
            window.location.href = '/medic/login';
        }
        
        this.medicService.getAllMedicalCenterNames().subscribe((options: string[]) => {
            console.log('Centros médicos obtenidos:', options);
            this.medicalCenterOptions = options;
        });
        
        // Cargar turnos inmediatamente y también después de un retraso
        this.loadAllTurnos();
        
        // Método de respaldo por si el primero falla
        setTimeout(() => {
            console.log('=== MÉTODO DE RESPALDO ===');
            this.loadAllTurnos();
        }, 1000);
    }

    ngAfterViewInit(): void {
        // Después de que la vista se haya inicializado, hacer una actualización del calendario
        setTimeout(() => {
            this.refreshCalendar();
        }, 100);
        
        // Exponer el componente en el window para debugging
        (window as any).calendarComponent = this;
        
        // Exponer métodos útiles para debugging
        (window as any).debugCalendar = {
            loadTurnos: () => this.loadAllTurnos(),
            loadTestData: () => this.loadTestData(),
            refreshCalendar: () => this.refreshCalendar(),
            showLocalStorage: () => console.log('LocalStorage:', {
                userId: localStorage.getItem('userId'),
                userType: localStorage.getItem('userType'),
                token: localStorage.getItem('token'),
                medicId: localStorage.getItem('medicId')
            }),
            showCurrentData: () => console.log('Datos actuales:', {
                allTurnos: this.allTurnos,
                my_events: this.my_events,
                calendarOptions: this.calendarOptions
            })
        };
    }

    refreshCalendar(): void {
        if (this.calendarComponent && this.my_events.length > 0) {
            const calendarApi = this.calendarComponent.getApi();
            calendarApi.removeAllEvents();
            calendarApi.addEventSource(this.my_events);
        }
    }

    loadAllTurnos() {
        console.log('=== INICIANDO loadAllTurnos ===');
        
        // Usar el método robusto para obtener medicId
        const medicId = this.getMedicId();
        
        console.log('medicId obtenido:', medicId);
        console.log('Todos los valores de localStorage:', {
            userId: localStorage.getItem('userId'),
            medicId: localStorage.getItem('medicId'),
            id: localStorage.getItem('id'),
            userType: localStorage.getItem('userType')
        });

        console.log('Llamando al servicio getAllTurnosByMedic con medicId:', medicId);
        
        // Forzar la llamada al backend
        this.medicService.getAllTurnosByMedic(medicId).subscribe({
            next: (turnos) => {
                console.log('=== RESPUESTA DEL BACKEND ===');
                console.log('Turnos recibidos:', turnos);
                console.log('Cantidad de turnos:', turnos ? turnos.length : 0);
                
                if (turnos && turnos.length > 0) {
                    console.log('Primer turno como ejemplo:', turnos[0]);
                }
                
                this.allTurnos = turnos || [];
                console.log('allTurnos asignado:', this.allTurnos);
                
                // Forzar actualización del calendario
                this.processCalendarEvents();
                console.log('Eventos procesados para el calendario:', this.my_events);
                
                // Actualización adicional después de un breve retraso
                setTimeout(() => {
                    this.refreshCalendar();
                }, 100);
            },
            error: (err) => {
                console.error('=== ERROR AL CARGAR TURNOS ===');
                console.error('Error completo:', err);
                console.error('Status:', err.status);
                console.error('Message:', err.message);
                console.error('URL:', err.url);
                
                // En caso de error, intentar cargar datos de prueba
                this.loadTestData();
            }
        });
    }

    processCalendarEvents() {
        console.log('Iniciando processCalendarEvents con turnos:', this.allTurnos);
        
        // Limpiar eventos anteriores
        this.my_events = [];
        
        // Agrupar turnos por fecha y centro médico para crear eventos más informativos
        const groupedTurnos = this.groupTurnosByDateAndCenter();
        console.log('Turnos agrupados:', groupedTurnos);
        
        Object.keys(groupedTurnos).forEach(date => {
            Object.keys(groupedTurnos[date]).forEach(center => {
                const turnosForDateCenter = groupedTurnos[date][center];
                const availableCount = turnosForDateCenter.filter((t: any) => t.available).length;
                const reservedCount = turnosForDateCenter.filter((t: any) => !t.available).length;
                
                // Crear evento resumen para el día
                const timeSlots = this.getTimeSlotSummary(turnosForDateCenter);
                const title = `${this.formatMedicalCenter(center)}\n${timeSlots}\nDisp: ${availableCount} | Res: ${reservedCount}`;
                
                this.my_events.push({
                    title: title,
                    date: date,
                    color: this.getEventColor(availableCount, reservedCount),
                    allDay: true,
                    extendedProps: {
                        turnos: turnosForDateCenter,
                        center: center,
                        date: date
                    }
                });
            });
        });
        
        // Crear una nueva referencia del array para forzar la actualización
        this.calendarOptions = {
            ...this.calendarOptions,
            events: [...this.my_events]
        };
        
        // Si el componente de calendario está disponible, forzar la actualización directamente
        if (this.calendarComponent) {
            const calendarApi = this.calendarComponent.getApi();
            calendarApi.removeAllEvents();
            calendarApi.addEventSource(this.my_events);
        }
        
        console.log('Eventos finales para el calendario:', this.my_events);
    }

    groupTurnosByDateAndCenter() {
        const grouped: any = {};
        
        this.allTurnos.forEach(turno => {
            const date = turno.fechaTurno;
            const center = turno.medicalCenter;
            
            if (!grouped[date]) {
                grouped[date] = {};
            }
            if (!grouped[date][center]) {
                grouped[date][center] = [];
            }
            
            grouped[date][center].push(turno);
        });
        
        return grouped;
    }

    formatMedicalCenter(center: string): string {
        // Mapear nombres de enum a nombres legibles
        const centerMap: {[key: string]: string} = {
            "SEDE_PRINCIPAL_HOSPITAL_AUSTRAL": "Hospital Austral",
            "CENTRO_ESPECIALIDAD_OFFICIA": "Centro Officia",
            "CENTRO_ESPECIALIDAD_CHAMPAGNAT": "Centro Champagnat",
            "CENTRO_ESPECIALIDAD_LUJAN": "Centro Lujan"
        };
        
        return centerMap[center] || center;
    }

    getTimeSlotSummary(turnos: any[]): string {
        if (turnos.length === 0) return '';
        
        const times = turnos.map(t => t.horaTurno).sort();
        const firstTime = times[0];
        const lastTime = times[times.length - 1];
        
        return `${firstTime} - ${lastTime}`;
    }

    getEventColor(availableCount: number, reservedCount: number): string {
        if (availableCount === 0) return '#ff5722'; // Solo reservados - rojo
        if (reservedCount === 0) return '#4caf50'; // Solo disponibles - verde
        return '#ff9800'; // Mixto - naranja
    }

    handleEventClick(arg: any) {
        const turnos = arg.event.extendedProps.turnos;
        const center = arg.event.extendedProps.center;
        const date = arg.event.extendedProps.date;
        
        this.selectedDate = date;
        this.selectedDateTurnos = turnos;
        this.showDetailModal = true;
    }

    handleDateClick(arg: any) {
        // Buscar turnos para la fecha seleccionada
        const dateStr = arg.dateStr;
        const turnosForDate = this.allTurnos.filter(t => t.fechaTurno === dateStr);
        
        if (turnosForDate.length > 0) {
            this.selectedDate = dateStr;
            this.selectedDateTurnos = turnosForDate;
            this.showDetailModal = true;
        }
    }

    getFilteredTurnos(): any[] {
        switch(this.viewType) {
            case 'available':
                return this.selectedDateTurnos.filter(t => t.available);
            case 'reserved':
                return this.selectedDateTurnos.filter(t => !t.available);
            default:
                return this.selectedDateTurnos;
        }
    }

    onTurnoSelectionChange(turno: any, event: any) {
        if (event.target.checked) {
            if (!this.selectedTurnos.find(t => t.id === turno.id)) {
                this.selectedTurnos.push(turno);
            }
        } else {
            this.selectedTurnos = this.selectedTurnos.filter(t => t.id !== turno.id);
        }
    }

    isTurnoSelected(turno: any): boolean {
        return this.selectedTurnos.some(t => t.id === turno.id);
    }

    areAllVisibleTurnosSelected(): boolean {
        const filteredTurnos = this.getFilteredTurnos();
        return filteredTurnos.length > 0 && filteredTurnos.every(turno => this.isTurnoSelected(turno));
    }

    onMasterCheckboxChange(event: any) {
        if (event.target.checked) {
            this.selectAllVisible();
        } else {
            this.deselectAllVisible();
        }
    }

    selectAllVisible() {
        const visibleTurnos = this.getFilteredTurnos();
        visibleTurnos.forEach(turno => {
            if (!this.selectedTurnos.find(t => t.id === turno.id)) {
                this.selectedTurnos.push(turno);
            }
        });
    }

    deselectAllVisible() {
        const visibleTurnosIds = this.getFilteredTurnos().map(t => t.id);
        this.selectedTurnos = this.selectedTurnos.filter(t => !visibleTurnosIds.includes(t.id));
    }

    processSelectedTurnos() {
        if (this.selectedTurnos.length === 0) {
            alert('No hay turnos seleccionados');
            return;
        }
        
        console.log('Turnos seleccionados:', this.selectedTurnos);
        // Aquí puedes agregar la lógica para procesar los turnos seleccionados
        // Por ejemplo, eliminarlos, modificarlos, etc.
        
        // Ejemplo: mostrar información de los turnos seleccionados
        const selectedInfo = this.selectedTurnos.map(t => 
            `${t.fechaTurno} ${t.horaTurno} - ${t.medicalCenter} (${t.available ? 'Disponible' : 'Reservado'})`
        ).join('\n');
        
        alert(`Turnos seleccionados:\n${selectedInfo}`);
    }

    closeDetailModal() {
        this.showDetailModal = false;
        this.selectedTurnos = [];
        this.viewType = 'all';
    }

    // Método para testing - lo llamaremos desde la consola del navegador
    testLoadTurnos() {
        console.log('=== TEST MANUAL DE CARGA DE TURNOS ===');
        this.loadAllTurnos();
    }

    // Método para obtener medicId de manera robusta
    getMedicId(): string {
        let medicId = localStorage.getItem('userId') || 
                     localStorage.getItem('medicId') || 
                     localStorage.getItem('id') || 
                     localStorage.getItem('user_id');
        
        if (!medicId) {
            console.warn('No se encontró medicId, usando valor por defecto');
            medicId = "1";
        }
        
        return medicId;
    }

    // Método para forzar actualización del calendario
    forceRefreshCalendar() {
        console.log('=== FORZANDO ACTUALIZACIÓN DEL CALENDARIO ===');
        this.loadAllTurnos();
        setTimeout(() => {
            this.refreshCalendar();
        }, 200);
    }

    // Método para cargar datos de prueba en caso de error
    loadTestData() {
        console.log('=== CARGANDO DATOS DE PRUEBA ===');
        
        // Crear algunos turnos de prueba
        const today = new Date();
        const testTurnos = [
            {
                turnoId: 1,
                fechaTurno: "2025-07-16",
                horaTurno: "09:00:00",
                medicFullName: "Dr. Test",
                medicSpecialty: "CARDIOLOGIA",
                medicalCenter: "CENTRO_ESPECIALIDAD_OFFICIA",
                available: true
            },
            {
                turnoId: 2,
                fechaTurno: "2025-07-16",
                horaTurno: "10:00:00",
                medicFullName: "Dr. Test",
                medicSpecialty: "CARDIOLOGIA",
                medicalCenter: "CENTRO_ESPECIALIDAD_OFFICIA",
                available: false,
                patient: {
                    name: "Juan",
                    lastname: "Pérez"
                }
            },
            {
                turnoId: 3,
                fechaTurno: "2025-07-18",
                horaTurno: "14:00:00",
                medicFullName: "Dr. Test",
                medicSpecialty: "CARDIOLOGIA",
                medicalCenter: "SEDE_PRINCIPAL_HOSPITAL_AUSTRAL",
                available: true
            }
        ];
        
        console.log('Datos de prueba creados:', testTurnos);
        this.allTurnos = testTurnos;
        this.processCalendarEvents();
        
        setTimeout(() => {
            this.refreshCalendar();
        }, 100);
    }

    addAvailableSlots() {
        // Mapear daysOfWeek seleccionados a formato backend
        const dayMap: any = {
            '1': 'MONDAY',
            '2': 'TUESDAY',
            '3': 'WEDNESDAY',
            '4': 'THURSDAY',
            '5': 'FRIDAY',
            '6': 'SATURDAY',
            '0': 'SUNDAY'
        };
        const selectedDays = this.availableDays.map(d => dayMap[d]);
        
        // Calcular fechas de inicio y fin (próxima semana)
        const today = new Date();
        const nextMonday = new Date(today);
        nextMonday.setDate(today.getDate() + ((8 - today.getDay()) % 7));
        const startDate = new Date(nextMonday);
        const endDate = new Date(nextMonday);
        endDate.setDate(startDate.getDate() + 6);
        
        // Construir objeto para backend
        const scheduleDTO = {
            startDate: startDate.toISOString().slice(0, 10),
            endDate: endDate.toISOString().slice(0, 10),
            daysOfWeek: selectedDays,
            startTime: this.startTime,
            endTime: this.endTime,
            durationMinutes: this.slotDuration,
            medicalCenter: this.medicalCenterEnumMap[this.medicalCenter]
        };
        
        console.log('=== CREANDO SCHEDULE ===');
        console.log('DTO a enviar:', scheduleDTO);
        console.log('Días seleccionados originales:', this.availableDays);
        console.log('Días mapeados:', selectedDays);
        console.log('Centro médico seleccionado:', this.medicalCenter);
        console.log('Centro médico mapeado:', this.medicalCenterEnumMap[this.medicalCenter]);
        
        this.medicService.createSchedule(scheduleDTO).subscribe({
            next: (response) => {
                console.log('Turnos creados exitosamente:', response);
                this.showModal = false;
                
                // Limpiar formulario
                this.availableDays = [];
                this.startTime = '';
                this.endTime = '';
                this.slotDuration = 30;
                this.medicalCenter = '';
                
                // Recargar los turnos con un pequeño retraso para asegurar que el backend esté actualizado
                setTimeout(() => {
                    console.log('Recargando turnos después de crear nuevos...');
                    this.loadAllTurnos();
                    
                    // Forzar actualización del calendario después de recargar
                    setTimeout(() => {
                        this.refreshCalendar();
                    }, 200);
                }, 500);
            },
            error: (err) => {
                console.error('Error al crear turnos:', err);
                alert('Error al crear los turnos. Por favor, intente nuevamente.');
            }
        });
    }

    onDayCheckboxChange(event: any, value: string) {
        if (event.target.checked) {
            if (!this.availableDays.includes(value)) {
                this.availableDays.push(value);
            }
        } else {
            this.availableDays = this.availableDays.filter(day => day !== value);
        }
    }
}
