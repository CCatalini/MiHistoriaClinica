import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: './home-patient.component.html',
    styleUrls: ['./home-patient.component.css']
})
export class HomePatientComponent implements OnInit {

    constructor(private router: Router) { }

    ngOnInit(): void {
        // Verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        }
    }
}
