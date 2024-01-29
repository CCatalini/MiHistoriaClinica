import {Component, OnInit} from '@angular/core';
import {CalendarOptions} from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";

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
        plugins: [dayGridPlugin]
    };

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }

    handleClick(arg:any){
        console.log(arg);
        console.log(arg.event._def.title);
    }
}
