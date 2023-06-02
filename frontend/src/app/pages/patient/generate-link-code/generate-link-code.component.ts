import { Component, OnInit } from '@angular/core';
import { PatientService } from "../../../services/patient/patient.service";

@Component({
    selector: 'app-generate-link-code',
    templateUrl: './generate-link-code.component.html',
    styleUrls: ['./generate-link-code.component.css']
})
export class GenerateLinkCodeComponent implements OnInit {
    linkCode: string = '';

    constructor(private patientService: PatientService) { }

    ngOnInit(): void {
        this.generateLinkCode();
    }

    generateLinkCode() {
        this.patientService.generateLinkCode(localStorage.getItem('token')).subscribe(
            (response: any) => {
                this.linkCode = response.linkCode;
            },
            error => {
                console.error('Error al generar el código:', error);
            }
        );
    }
}
