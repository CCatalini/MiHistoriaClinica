import { Component, OnInit } from '@angular/core';
import { PatientService } from "../../../services/patient/patient.service";

@Component({
    selector: 'app-generate-link-code',
    templateUrl: './generate-link-code.component.html',
    styleUrls: ['./generate-link-code.component.css']
})


export class GenerateLinkCodeComponent implements OnInit {
    linkCode: string = '';

    constructor(private patientService: PatientService) {
    }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
        this.generateLinkCode();
    }

    generateLinkCode() {
        this.patientService.generateLinkCode().subscribe(
            (response: string) => {
                this.linkCode = response;
            },
            (error: any) => {
                if (error instanceof ErrorEvent) {
                    // Error de red
                    console.error('Error de red:', error.message);
                } else {
                    // Error de análisis
                    console.error('Error al analizar la respuesta del servidor:', error);
                }
            }
        );
    }


}


/*
LO QUE TENEMOS

generateLinkCode() {
        const token = localStorage.getItem('token');
        console.log('', token);
        this.patientService.generateLinkCode(token).subscribe(
            (response: any) => {
                this.linkCode = response.linkCode;
            },
            error => {
                console.error('Error al generar el código:', error);
            }
        );
    }
 */

/*
generateLinkCode() {
        this.patientService.generateLinkCode().subscribe(
            (response: any) => {
                this.linkCode = response.linkCode;
            },
            error => {
                console.error('Error al generar el código:', error);
            }
        );
    }

 */
