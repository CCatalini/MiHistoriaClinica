import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {PatientService} from "../../services/patient/patient.service";
import {MedicService} from "../../services/medic/medic.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    constructor(private router: Router, private route: ActivatedRoute, private patientService: PatientService, private medicService: MedicService) {}

    ngOnInit(): void {}

    isPatientPage(): boolean {
        return this.router.url.includes('/patient')&&(!this.router.url.includes('/login')&&(!this.router.url.includes('/signup')));
    }

    isMedicPage(): boolean {
        return this.router.url.includes('/medic')&&(!this.router.url.includes('/login')&&(!this.router.url.includes('/signup'))&&!this.router.url.includes('/patient'));
    }

    logoutPatient(): void {
        this.patientService.logoutPatient().subscribe(
            () => {
                localStorage.setItem('token', '');
                localStorage.setItem('userType', '');
                this.router.navigate(['/']);
            },
            (error) => {
                console.error('Error al cerrar sesión:', error);
            }
        );
    }

    logoutMedic(): void {
        this.medicService.logoutMedic().subscribe(
            () => {
                localStorage.setItem('token', '');
                localStorage.setItem('userType', '');
                this.router.navigate(['/']);
            },
            (error) => {
                console.error('Error al cerrar sesión:', error);
            }
        );
    }

}

