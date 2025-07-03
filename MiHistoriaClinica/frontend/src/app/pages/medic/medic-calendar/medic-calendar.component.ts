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

    constructor(private medicService: MedicService) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
        this.medicService.getAllMedicalCenterNames().subscribe((options: string[]) => {
            this.medicalCenterOptions = options;
        });
    }

    handleClick(arg:any){
        console.log(arg);
        console.log(arg.event._def.title);
    }

    addAvailableSlots() {
        // Por simplicidad, agregamos los turnos para la próxima semana
        const today = new Date();
        const nextMonday = new Date(today);
        nextMonday.setDate(today.getDate() + ((8 - today.getDay()) % 7));
        for (let i = 0; i < 7; i++) {
            const date = new Date(nextMonday);
            date.setDate(nextMonday.getDate() + i);
            const dayOfWeek = date.getDay().toString();
            if (this.availableDays.includes(dayOfWeek)) {
                // Generar slots para este día
                const [startHour, startMinute] = this.startTime.split(':').map(Number);
                const [endHour, endMinute] = this.endTime.split(':').map(Number);
                let slot = new Date(date);
                slot.setHours(startHour, startMinute, 0, 0);
                const endSlot = new Date(date);
                endSlot.setHours(endHour, endMinute, 0, 0);
                while (slot < endSlot) {
                    const slotStr = slot.toISOString().slice(0, 16);
                    this.my_events.push({
                        title: `Disponible (${this.medicalCenter})`,
                        date: slotStr,
                        color: '#4caf50'
                    });
                    slot = new Date(slot.getTime() + this.slotDuration * 60000);
                }
            }
        }
        // Actualizar el calendario
        this.calendarOptions.events = [...this.my_events];
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
