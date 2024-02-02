import { Component, OnInit } from '@angular/core';
import { PatientService } from "../../../services/patient/patient.service";

@Component({
    selector: 'app-generate-link-code',
    templateUrl: './generate-link-code.component.html',
    styleUrls: ['./generate-link-code.component.css']
})
export class GenerateLinkCodeComponent implements OnInit {
    linkCode: string = '';

    constructor(private patientService: PatientService) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'PATIENT') {
            window.location.href = '/patient/login';
        }
    }

    generateLinkCode(): void {
        this.patientService.generateLinkCode().subscribe(
            (response: string) => {
                this.linkCode = response;
                localStorage.setItem('patientLinkCode', this.linkCode);
                // Additional actions with the link code if needed
            },
            (error) => {
                console.error('Error al generar el código del paciente:', error);
            }
        );
    }

}




/*export class GenerateLinkCodeComponent implements OnInit {
    linkCode: string = '';

    constructor(private patientService: PatientService) {
    }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }
        this.generateLinkCode();
    }

    generateLinkCode(): void {
        this.patientService.generateLinkCode().subscribe(
            (response: string) => {
                this.linkCode = response;
            },
            (error) => {
                console.error('Error al generar el código del paciente:', error);
            }
        );
    }

}
*/


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
