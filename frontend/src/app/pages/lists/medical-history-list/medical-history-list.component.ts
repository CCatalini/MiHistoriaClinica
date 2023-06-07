import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import {PatientService} from "../../../services/patient/patient.service";

@Component({
    selector: 'app-medical-history-list',
    templateUrl: './medical-history-list.component.html',
    styleUrls: ['./medical-history-list.component.css']
})
export class MedicalHistoryListComponent implements OnInit {
    public medicalHistory: any = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    };

    constructor(private patientService: PatientService, private router: Router) {}

    ngOnInit(): void {
        // Verify user
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.fetchMedicalHistory(); // Fetch medical history data
        }
    }

    private fetchMedicalHistory(): void {
        // Get the authorization token from localStorage
        const token = localStorage.getItem('token');
        if(token){
            this.patientService.getMedicalHistory(token).subscribe(
                (response) => {
                    this.medicalHistory = response; // Assign the response data to medicalHistory object
                },
                (error) => {
                    console.log('Error occurred while fetching medical history:', error);
                }
            );
        }else{
            console.error('Token no encontrado');
        }

    }

}
