import {Component, OnInit} from '@angular/core';
import {CalendarOptions} from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import esLocale from '@fullcalendar/core/locales/es';
import { MedicService } from '../../../services/medic/medic.service';

@Component({
  selector: 'app-medic-calendar',
  templateUrl: './medic-calendar.component.html',
  styleUrls: ['./medic-calendar.component.css']
})

export class MedicCalendarComponent implements OnInit{

    my_events : any = [
        { title: 'Evento1', date: '2024-01-29', color: '#3fb5eb'},
        { title: 'Evento2', date: '2024-01-25', color: '#3fb5eb'},
        { title: 'Evento3', date: '2024-01-20', color: '#3fb5eb'}
    ]

    calendarOptions: CalendarOptions = {
        initialView: 'dayGridMonth',
        events: this.my_events,
        eventClick: this.handleClick.bind(this),
        locale: esLocale,
        plugins: [dayGridPlugin]
    };

    // Variables para el modal y formulario
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
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
        this.medicService.getAllMedicalCenterNames().subscribe((options: string[]) => {
            this.medicalCenterOptions = options;
        });
        this.loadAvailableTurnos();
    }

    loadAvailableTurnos() {
        const medicId = localStorage.getItem('userId');
        if (!medicId) return;
        this.medicService.getAllTurnosByMedic(medicId).subscribe({
            next: (turnos: any[]) => {
                this.my_events = turnos.map(turno => {
                    if (turno.available) {
                        return {
                            title: `Disponible (${turno.medicalCenter})`,
                            date: turno.fechaTurno + 'T' + turno.horaTurno,
                            color: '#4caf50'
                        };
                    } else {
                        return {
                            title: `Reservado: ${turno.patient ? turno.patient.name + ' ' + turno.patient.lastname : ''}`,
                            date: turno.fechaTurno + 'T' + turno.horaTurno,
                            color: '#ff9800'
                        };
                    }
                });
                this.calendarOptions.events = [...this.my_events];
            },
            error: (err) => {
                console.error('Error al cargar turnos', err);
            }
        });
    }

    handleClick(arg:any){
        console.log(arg);
        console.log(arg.event._def.title);
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
        this.medicService.createSchedule(scheduleDTO).subscribe({
            next: () => {
                // Opcional: mostrar mensaje de éxito
                this.showModal = false;
                this.loadAvailableTurnos();
            },
            error: (err) => {
                // Opcional: mostrar error
                console.error(err);
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
