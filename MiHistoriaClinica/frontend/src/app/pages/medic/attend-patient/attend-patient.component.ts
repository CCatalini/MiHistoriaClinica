import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Component({
    selector: 'app-attend-patient',
    templateUrl: './attend-patient.component.html',
    styleUrls: ['./attend-patient.component.css']
})
export class AttendPatientComponent implements OnInit {
    patientId: string = '';
    patient: any;

    constructor(
        private route: ActivatedRoute,
        private httpClient: HttpClient
    ) { }

    ngOnInit(): void {
        // Verify user
        if (localStorage.getItem('userType') !== 'MEDIC') {
            window.location.href = '/medic/login';
        } else {
            this.route.params.subscribe(params => {
                this.patientId = params['linkCode'];
            });

            // Call the method to fetch patient info
            this.getPatientInfo();
        }
    }

    setLinkCode(): void {
        localStorage.setItem('patientLinkCode', '');
    }

    getPatientInfo(): void {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        this.httpClient.get<any>('http://localhost:8080/patient/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
    }
}
