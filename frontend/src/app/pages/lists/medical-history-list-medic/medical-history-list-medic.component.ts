import {Component, OnInit} from '@angular/core';
import {PatientService} from "../../../services/patient/patient.service";
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";

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

    constructor(private medicService: MedicService, private router: Router) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/patient/login']);
        } else {
            this.fetchMedicalHistory(); // Fetch medical history data
        }
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
}
