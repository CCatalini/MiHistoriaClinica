import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
    selector: 'app-medical-history-list-medic',
    templateUrl: './medical-history-list-medic.component.html',
    styleUrls: ['./medical-history-list-medic.component.css']
})
export class MedicalHistoryListMedicComponent implements OnInit{
    public medicalHistory: any = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    };

    patient : any;

    constructor(private medicService: MedicService, private router: Router, private httpClient: HttpClient) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/patient/login']);
        } else {
            this.fetchMedicalHistory(); // Fetch medical history data
        }

        this.getPatientInfo();
    }

    private fetchMedicalHistory(): void {
        this.medicService.getPatientMedicalHistory().subscribe(
            (response) => {
                this.medicalHistory = response;
            },
            (error) => {
                console.log('Error occurred while fetching medical history:', error);
            }
        );
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
