import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-home',
    templateUrl: './home-patient.component.html',
    styleUrls: ['./home-patient.component.css']
})
export class HomePatientComponent implements OnInit {

    constructor() { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }
    }

}