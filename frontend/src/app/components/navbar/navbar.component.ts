import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {PatientService} from "../../services/patient/patient.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    constructor(private router: Router, private route: ActivatedRoute, private patientService: PatientService) {}

    ngOnInit(): void {}

    isPatientHomePage(): boolean {
        return this.router.url.includes('/patient');
    }

    logoutPatient(): void {
        this.patientService.logoutPatient().subscribe(
            () => {
                this.router.navigate(['/patient/login']);
            },
            (error) => {
                console.error('Error al cerrar sesión:', error);
            }
        );
    }

}
