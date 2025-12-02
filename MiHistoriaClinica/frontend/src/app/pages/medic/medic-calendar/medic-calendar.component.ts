import {Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef, OnDestroy} from '@angular/core';
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

export class MedicCalendarComponent implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

    my_events : any = [];
    allTurnos: any[] = [];
    currentView: string = 'dayGridMonth';
    calendarFilter: 'all' | 'available' | 'reserved' = 'all';
    private autoRefreshInterval: any = null;

    calendarOptions: CalendarOptions = {
        initialView: 'dayGridMonth',
        events: [],
        eventClick: this.handleEventClick.bind(this),
        dateClick: this.handleDateClick.bind(this),
        viewDidMount: this.handleViewChange.bind(this),
        locale: esLocale,
        plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        height: '700px',
        eventDisplay: 'block',
        dayMaxEventRows: 3,
        moreLinkClick: 'day',
        slotMinTime: '08:00:00',
        slotMaxTime: '20:00:00',
        slotDuration: '00:30:00',
        allDaySlot: false,
        displayEventTime: true
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

    // Variables para gestión de turnos individuales
    showReserveModal: boolean = false;
    showBlockModal: boolean = false;
    showCancelModal: boolean = false;
    showErrorModal: boolean = false;
    errorMessage: string = '';
    errorTitle: string = 'Error';
    myPatients: any[] = [];
    selectedPatientDni: string = '';
    turnoToReserve: any = null;
    turnoToBlock: any = null;
    turnoToCancel: any = null;

    // Mapeo de nombre legible a nombre de enum para MedicalCenterE
    medicalCenterEnumMap: {[key: string]: string} = {
      "Sede Principal - Hospital Universitario Austral": "SEDE_PRINCIPAL_HOSPITAL_AUSTRAL",
      "Centro de especialidad Officia": "CENTRO_ESPECIALIDAD_OFFICIA",
      "Centro de especialidad Champagnat": "CENTRO_ESPECIALIDAD_CHAMPAGNAT",
      "Centro de especialidad Lujan": "CENTRO_ESPECIALIDAD_LUJAN"
    };

    // Opciones fijas para duración
    durationOptions: number[] = [15, 30, 45, 60];

    constructor(
        private medicService: MedicService,
        private cdr: ChangeDetectorRef
    ) {}

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
        
        // Cargar turnos inmediatamente
        this.loadAllTurnos();
        
        // Método de respaldo por si el primero falla
        setTimeout(() => {
            console.log('=== MÉTODO DE RESPALDO ===');
            this.loadAllTurnos();
        }, 1000);
        
        // Listener para detectar cuando se recarga la página
        window.addEventListener('beforeunload', () => {
            console.log('Página siendo recargada...');
        });
        
        // Listener para detectar cuando la página se enfoca nuevamente
        window.addEventListener('focus', () => {
            console.log('Página enfocada, recargando turnos...');
            this.loadAllTurnos();
        });
        
        // Configurar actualización automática cada 30 segundos
        this.autoRefreshInterval = setInterval(() => {
            // Actualización silenciosa cada 15 segundos para reflejar cambios de pacientes
            this.loadAllTurnos();
        }, 15000);
    }
    
    ngOnDestroy(): void {
        // Limpiar el intervalo cuando el componente se destruye
        if (this.autoRefreshInterval) {
            clearInterval(this.autoRefreshInterval);
            console.log('🛑 Intervalo de actualización automática detenido');
        }
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
            refreshCalendar: () => this.refreshCalendar(),
            processEvents: () => this.processCalendarEvents(),
            showLocalStorage: () => console.log('LocalStorage:', {
                userId: localStorage.getItem('userId'),
                userType: localStorage.getItem('userType'),
                token: localStorage.getItem('token'),
                medicId: localStorage.getItem('medicId')
            }),
            showCurrentData: () => console.log('Datos actuales:', {
                allTurnos: this.allTurnos,
                my_events: this.my_events,
                currentView: this.currentView,
                calendarOptions: this.calendarOptions
            }),
            changeView: (view: string) => {
                if (this.calendarComponent) {
                    this.calendarComponent.getApi().changeView(view);
                }
            }
        };
    }

    refreshCalendar(): void {
        if (this.calendarComponent) {
            const calendarApi = this.calendarComponent.getApi();
            calendarApi.removeAllEvents();
            if (this.my_events.length > 0) {
                calendarApi.addEventSource(this.my_events);
            }
            calendarApi.refetchEvents();
            this.cdr.detectChanges();
        }
    }

    loadAllTurnos() {
        console.log('=== INICIANDO loadAllTurnos ===');
        
        console.log('Todos los valores de localStorage:', {
            userId: localStorage.getItem('userId'),
            medicId: localStorage.getItem('medicId'),
            id: localStorage.getItem('id'),
            userType: localStorage.getItem('userType')
        });

        console.log('Llamando al servicio getAllTurnosByMedic (usa token JWT)');
        
        // El backend extrae el medicId del token JWT
        this.medicService.getAllTurnosByMedic().subscribe({
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
                
                // Forzar detección de cambios
                this.cdr.detectChanges();
                
                // Actualización adicional después de un breve retraso
                setTimeout(() => {
                    this.refreshCalendar();
                    this.cdr.detectChanges();
                }, 100);
            },
            error: (err) => {
                console.error('=== ERROR AL CARGAR TURNOS ===');
                console.error('Error completo:', err);
                console.error('Status:', err.status);
                console.error('Message:', err.message);
                console.error('URL:', err.url);
                
                // En caso de error, inicializar con array vacío
                this.allTurnos = [];
                this.processCalendarEvents();
            }
        });
    }

    processCalendarEvents() {
        console.log('Iniciando processCalendarEvents con turnos:', this.allTurnos);
        console.log('Vista actual:', this.currentView);
        
        // Limpiar eventos anteriores
        this.my_events = [];
        
        if (this.currentView === 'dayGridMonth') {
            // Vista mensual: mostrar eventos agrupados (resumen)
            this.createMonthlyEvents();
        } else {
            // Vista semanal y diaria: mostrar eventos individuales
            this.createDetailedEvents();
        }
        
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
        
        // Forzar detección de cambios
        this.cdr.detectChanges();
        
        console.log('Eventos finales para el calendario:', this.my_events);
    }

    createMonthlyEvents() {
        // Agrupar turnos por fecha y centro médico para crear eventos más informativos
        const groupedTurnos = this.groupTurnosByDateAndCenter();
        console.log('Turnos agrupados para vista mensual:', groupedTurnos);
        
        Object.keys(groupedTurnos).forEach(date => {
            Object.keys(groupedTurnos[date]).forEach(center => {
                let turnosForDateCenter = groupedTurnos[date][center];
                
                // Aplicar filtro
                if (this.calendarFilter === 'available') {
                    turnosForDateCenter = turnosForDateCenter.filter((t: any) => t.available);
                } else if (this.calendarFilter === 'reserved') {
                    turnosForDateCenter = turnosForDateCenter.filter((t: any) => !t.available);
                }
                
                // Si no hay turnos después del filtro, no crear evento
                if (turnosForDateCenter.length === 0) return;
                
                const availableCount = turnosForDateCenter.filter((t: any) => t.available).length;
                const reservedCount = turnosForDateCenter.filter((t: any) => !t.available).length;
                
                // Crear evento resumen para el día
                const timeSlots = this.getTimeSlotSummary(turnosForDateCenter);
                const title = `${this.formatMedicalCenterShort(center)}\n${timeSlots}\nDisp: ${availableCount} | Res: ${reservedCount}`;
                
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
    }

    createDetailedEvents() {
        console.log('Creando eventos detallados para vista semanal/diaria');
        
        let turnosToShow = this.allTurnos;
        
        // Aplicar filtro
        if (this.calendarFilter === 'available') {
            turnosToShow = this.allTurnos.filter(t => t.available);
        } else if (this.calendarFilter === 'reserved') {
            turnosToShow = this.allTurnos.filter(t => !t.available);
        }
        
        turnosToShow.forEach(turno => {
            const title = this.getTurnoTitle(turno);
            const color = turno.available ? '#9e9e9e' : '#3fb5eb';
            
            // Calcular tiempo de fin basado en la duración del turno
            const startTime = new Date(`${turno.fechaTurno}T${turno.horaTurno}`);
            const duracionMinutos = turno.duracion || 30; // Usar duración del turno o 30 minutos por defecto
            const endTime = new Date(startTime.getTime() + (duracionMinutos * 60 * 1000));
            
            this.my_events.push({
                title: title,
                start: `${turno.fechaTurno}T${turno.horaTurno}`,
                end: endTime.toISOString(),
                color: color,
                allDay: false,
                extendedProps: {
                    turno: turno,
                    center: turno.medicalCenter,
                    date: turno.fechaTurno
                }
            });
        });
    }

    getTurnoTitle(turno: any): string {
        const centerShort = this.formatMedicalCenterShort(turno.medicalCenter);
        const hora = turno.horaTurno.substring(0, 5); // Solo HH:MM
        const duracion = turno.duracion || 30;
        
        // Mostrar duración solo si es diferente a 30 minutos
        const duracionText = duracion !== 30 ? ` (${duracion}min)` : '';
        
        if (turno.available) {
            return `${centerShort} ${hora}${duracionText}\nDisponible`;
        } else {
            const patientName = turno.patientName ? `${turno.patientName} ${turno.patientLastname}` : 'Paciente';
            return `${centerShort} ${hora}${duracionText}\n${patientName}`;
        }
    }

    formatMedicalCenterShort(center: string): string {
        const centerMap: {[key: string]: string} = {
            "SEDE_PRINCIPAL_HOSPITAL_AUSTRAL": "H. Austral",
            "CENTRO_ESPECIALIDAD_OFFICIA": "C. Officia",
            "CENTRO_ESPECIALIDAD_CHAMPAGNAT": "C. Champagnat",
            "CENTRO_ESPECIALIDAD_LUJAN": "C. Lujan"
        };
        
        return centerMap[center] || center;
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
            "SEDE_PRINCIPAL_HOSPITAL_AUSTRAL": "Sede Principal - Hospital Universitario Austral",
            "CENTRO_ESPECIALIDAD_OFFICIA": "Centro de Especialidad Officia",
            "CENTRO_ESPECIALIDAD_CHAMPAGNAT": "Centro de Especialidad Champagnat",
            "CENTRO_ESPECIALIDAD_LUJAN": "Centro de Especialidad Lujan"
        };
        
        return centerMap[center] || center;
    }

    getFormattedDate(dateStr: string): string {
        // Formatear fecha en español
        const date = new Date(dateStr + 'T12:00:00'); // Añadir hora para evitar problemas de zona horaria
        const options: Intl.DateTimeFormatOptions = { 
            weekday: 'long', 
            year: 'numeric', 
            month: 'long', 
            day: 'numeric' 
        };
        return date.toLocaleDateString('es-ES', options);
    }

    formatTime(time: string): string {
        // Formatear hora sin segundos (HH:mm en lugar de HH:mm:ss)
        if (!time) return '';
        return time.substring(0, 5); // Toma solo los primeros 5 caracteres (HH:mm)
    }

    getRowBackgroundColor(turno: any): string {
        if (turno.available) {
            // Turno disponible - gris claro
            return 'rgba(210, 210, 210, 0.5)';
        } else if (turno.patientName) {
            // Turno reservado (con paciente) - celeste principal con poca opacidad
            // #3fb5eb = rgb(63, 181, 235)
            return 'rgba(63, 181, 235, 0.2)';
        } else {
            // Turno bloqueado (sin paciente) - gris oscuro
            return 'rgba(73, 80, 87, 0.4)';
        }
    }

    getBadgeColor(turno: any): string {
        if (turno.available) {
            return '#9e9e9e'; // Gris para disponible
        } else if (turno.patientName) {
            return '#3fb5eb'; // Celeste principal para reservado
        } else {
            return '#495057'; // Gris oscuro para bloqueado
        }
    }

    getBadgeText(turno: any): string {
        if (turno.available) {
            return 'Disponible';
        } else if (turno.patientName) {
            return 'Reservado';
        } else {
            return 'No disponible';
        }
    }

    getTimeSlotSummary(turnos: any[]): string {
        if (turnos.length === 0) return '';
        
        const times = turnos.map(t => this.formatTime(t.horaTurno)).sort();
        const firstTime = times[0];
        const lastTime = times[times.length - 1];
        
        return `${firstTime} - ${lastTime}`;
    }

    getEventColor(availableCount: number, reservedCount: number): string {
        if (availableCount === 0) return '#3fb5eb'; // Solo reservados - celeste principal
        if (reservedCount === 0) return '#9e9e9e'; // Solo disponibles - gris medio
        return '#757575'; // Mixto - gris oscuro
    }

    handleEventClick(arg: any) {
        const extendedProps = arg.event.extendedProps;
        
        if (extendedProps.turnos) {
            // Evento agrupado (vista mensual) - permitir selección
            const turnos = extendedProps.turnos;
            const center = extendedProps.center;
            const date = extendedProps.date;
            
            this.selectedDate = date;
            this.selectedDateTurnos = turnos;
            this.showDetailModal = true;
        } else if (extendedProps.turno) {
            // Evento individual (vista semanal/diaria) - solo mostrar información
            const turno = extendedProps.turno;
            this.showTurnoInfo(turno);
        }
    }

    showTurnoInfo(turno: any) {
        const centerName = this.formatMedicalCenter(turno.medicalCenter);
        const hora = turno.horaTurno.substring(0, 5);
        const duracion = turno.duracion || 30;
        const fecha = new Date(turno.fechaTurno).toLocaleDateString('es-ES', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        
        // Calcular hora de fin
        const startTime = new Date(`${turno.fechaTurno}T${turno.horaTurno}`);
        const endTime = new Date(startTime.getTime() + (duracion * 60 * 1000));
        const horaFin = endTime.toTimeString().substring(0, 5);
        
        let info = `Fecha: ${fecha}\nHora: ${hora} - ${horaFin} (${duracion} min)\nCentro: ${centerName}\n\n`;
        
        if (turno.available) {
            info += `Estado: Disponible\n`;
            info += `Este turno está disponible para ser reservado por un paciente.`;
        } else if (turno.patientName) {
            const patientName = `${turno.patientName} ${turno.patientLastname}`;
            info += `🔒 Estado: Reservado\n`;
            info += `👤 Paciente: ${patientName}\n`;
            if (turno.patientEmail) {
                info += `📧 Email: ${turno.patientEmail}\n`;
            }
            if (turno.patientDni) {
                info += `🆔 DNI: ${turno.patientDni}\n`;
            }
        } else {
            info += `Estado: No disponible (bloqueado)\n`;
            info += `Este turno está bloqueado y no puede ser reservado.`;
        }
        
        alert(info);
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

    handleViewChange(arg: any) {
        console.log('Vista cambiada a:', arg.view.type);
        this.currentView = arg.view.type;
        
        // Regenerar eventos para la nueva vista
        if (this.allTurnos.length > 0) {
            this.processCalendarEvents();
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

    // ============================================
    // MÉTODOS PARA ACCIONES INDIVIDUALES DE TURNOS
    // ============================================

    // Abrir modal para reservar turno individual
    abrirReservarTurno(turno: any) {
        this.turnoToReserve = turno;
        this.selectedPatientDni = ''; // Resetear selección
        
        // Cargar pacientes asociados
        const token = localStorage.getItem('token') || '';
        this.medicService.getPatientsList(token).subscribe({
            next: (patients) => {
                this.myPatients = patients || [];
                if (this.myPatients.length === 0) {
                    this.mostrarError('Sin pacientes', 'No tienes pacientes asociados. Vincula pacientes primero para poder asignarles turnos.');
                } else {
                    this.showReserveModal = true;
                }
            },
            error: (err) => {
                console.error('Error al cargar pacientes:', err);
                this.mostrarError('Error de Conexión', 'No se pudo cargar la lista de pacientes. Por favor, intenta nuevamente.');
            }
        });
    }

    // Confirmar reserva de turno individual
    confirmarReservaTurno() {
        if (!this.isPacienteSeleccionado()) {
            this.mostrarError('Paciente No Seleccionado', 'Debe seleccionar un paciente del listado antes de confirmar la reserva.');
            return;
        }

        if (!this.turnoToReserve) {
            this.mostrarError('Error Interno', 'No se encontró el turno a reservar. Por favor, cierra este modal e intenta nuevamente.');
            return;
        }

        // Buscar el paciente por DNI
        const paciente = this.myPatients.find(p => String(p.dni) === String(this.selectedPatientDni));
        
        if (!paciente) {
            this.mostrarError('Paciente No Encontrado', 'El paciente seleccionado no se encuentra en la lista. Por favor, recarga la página e intenta nuevamente.');
            return;
        }

        // Enviar DNI al backend
        const patientDniToSend = String(this.selectedPatientDni);

        // Reservar directamente
        this.medicService.reservarTurnoParaPaciente(this.turnoToReserve.turnoId, patientDniToSend).subscribe({
            next: () => {
                this.closeReserveModal();
                this.mostrarError('Reserva Exitosa', `El turno ha sido reservado para ${paciente.name} ${paciente.lastname} correctamente.`);
            },
            error: (err) => {
                const errorMsg = err.error?.message || err.error || err.message || 'No se pudo completar la reserva';
                this.mostrarError('Error al Reservar', `${errorMsg}. Por favor, verifica que el turno esté disponible e intenta nuevamente.`);
            }
        });
    }

    // Cerrar modal de reserva
    closeReserveModal() {
        this.showReserveModal = false;
        this.selectedPatientDni = '';
        this.turnoToReserve = null;
    }

    // Abrir modal para confirmar bloqueo
    abrirModalBloquear(turno: any) {
        this.turnoToBlock = turno;
        this.showBlockModal = true;
    }

    // Confirmar bloqueo de turno
    confirmarBloqueoTurno() {
        if (!this.turnoToBlock) {
            this.mostrarError('Error Interno', 'No se encontró el turno a bloquear. Por favor, intenta nuevamente.');
            return;
        }

        this.medicService.bloquearTurno(this.turnoToBlock.turnoId).subscribe({
            next: () => {
                this.closeBlockModal();
                this.mostrarError('Turno Bloqueado', 'El turno ha sido bloqueado correctamente y ya no estará disponible para reservas.');
            },
            error: (err) => {
                console.error('Error al bloquear turno:', err);
                const errorMsg = err.error?.message || err.message || 'No se pudo bloquear el turno';
                this.closeBlockModal();
                this.mostrarError('Error al Bloquear', `${errorMsg}. Por favor, intenta nuevamente.`);
            }
        });
    }

    // Cerrar modal de bloqueo
    closeBlockModal() {
        this.showBlockModal = false;
        this.turnoToBlock = null;
    }

    // Abrir modal para liberar un turno individual (reservado/bloqueado → disponible)
    liberarTurnoIndividual(turno: any) {
        this.turnoToCancel = turno;
        this.showCancelModal = true;
    }

    // Confirmar liberación de turno
    confirmarLiberarTurno() {
        if (!this.turnoToCancel) {
            this.mostrarError('Error Interno', 'No se encontró el turno a liberar. Por favor, intenta nuevamente.');
            return;
        }
        
        const tituloExito = this.turnoToCancel.patientName ? 
            'Reserva Cancelada' : 
            'Turno Desbloqueado';
        const mensajeExito = this.turnoToCancel.patientName ? 
            'La reserva ha sido cancelada y el turno está nuevamente disponible.' : 
            'El turno ha sido desbloqueado y ahora está disponible para reservas.';

        this.medicService.liberarTurno(this.turnoToCancel.turnoId).subscribe({
            next: () => {
                this.closeCancelModal();
                this.mostrarError(tituloExito, mensajeExito);
            },
            error: (err) => {
                console.error('Error al liberar turno:', err);
                const errorMsg = err.error?.message || err.message || 'No se pudo liberar el turno';
                this.closeCancelModal();
                this.mostrarError('Error al Liberar', `${errorMsg}. Por favor, intenta nuevamente.`);
            }
        });
    }

    // Cerrar modal de cancelación
    closeCancelModal() {
        this.showCancelModal = false;
        this.turnoToCancel = null;
    }

    // Mostrar modal de error
    mostrarError(titulo: string, mensaje: string) {
        this.errorTitle = titulo;
        this.errorMessage = mensaje;
        this.showErrorModal = true;
    }

    // Cerrar modal de error
    closeErrorModal() {
        const wasSuccess = this.errorTitle.includes('Exitosa') || this.errorTitle.includes('Bloqueado') || this.errorTitle.includes('Cargada');
        this.showErrorModal = false;
        this.errorMessage = '';
        this.errorTitle = 'Error';
        
        // Si fue un mensaje de éxito, recargar los turnos y cerrar el modal de detalle
        if (wasSuccess) {
            // Cerrar modal de detalle si está abierto
            this.showDetailModal = false;
            
            // Recargar turnos
            this.loadAllTurnos();
            
            // Forzar actualización adicional del calendario después de un breve delay
            setTimeout(() => {
                this.refreshCalendar();
                this.cdr.detectChanges();
            }, 500);
        }
    }

    // Handler para cuando cambia la selección de paciente
    // Verificar si hay un paciente seleccionado
    isPacienteSeleccionado(): boolean {
        return this.selectedPatientDni !== '' && 
               this.selectedPatientDni !== null && 
               this.selectedPatientDni !== undefined;
    }

    // Cerrar modal de detalle
    closeDetailModal() {
        this.showDetailModal = false;
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
        
        // El backend genera automáticamente turnos para 1 mes desde hoy
        // No necesitamos calcular fechas manualmente
        const today = new Date();
        const startDate = today.toISOString().slice(0, 10);
        
        // Construir objeto para backend
        const scheduleDTO = {
            startDate: startDate, // Fecha de inicio (hoy)
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
        console.log('Se crearán turnos para 30 días desde:', startDate);
        console.log('Nota: Se aplicará automáticamente espacio de 5 min entre turnos y pausa de 13:00-14:00');
        
        this.medicService.createSchedule(scheduleDTO).subscribe({
            next: (response) => {
                console.log('Turnos creados exitosamente:', response);
                
                // Cerrar modal y limpiar formulario
                this.showModal = false;
                this.availableDays = [];
                this.startTime = '';
                this.endTime = '';
                this.slotDuration = 30;
                this.medicalCenter = '';
                
                // Mostrar mensaje de éxito (el reload se hará al cerrar el modal)
                this.mostrarError('Agenda Cargada', 'La agenda ha sido cargada correctamente. Los nuevos turnos ya están disponibles.');
            },
            error: (err) => {
                console.error('Error al crear turnos:', err);
                let errorMsg = 'Error al crear los turnos.';
                if (err.error && err.error.message) {
                    errorMsg += ' ' + err.error.message;
                }
                this.mostrarError('Error al Crear Agenda', errorMsg + ' Por favor, intente nuevamente.');
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

    setCalendarFilter(filter: 'all' | 'available' | 'reserved') {
        console.log('Cambiando filtro de calendario a:', filter);
        this.calendarFilter = filter;
        
        // Volver a procesar los eventos del calendario con el nuevo filtro
        this.processCalendarEvents();
        
        // Forzar actualización del calendario
        setTimeout(() => {
            this.refreshCalendar();
        }, 100);
    }
}
