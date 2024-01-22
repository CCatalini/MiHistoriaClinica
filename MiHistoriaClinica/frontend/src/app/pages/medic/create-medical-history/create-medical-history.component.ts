import { Component } from '@angular/core';
import Swal from "sweetalert2";
import { MedicService } from "../../../services/medic/medic.service";
import { Router } from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
    selector: 'app-create-medical-history',
    templateUrl: './create-medical-history.component.html',
    styleUrls: ['./create-medical-history.component.css']
})
export class CreateMedicalHistoryComponent {
    public medicalHistoryModel = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    }

    bloodTypes: string[] = [];
    patient : any;

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'MEDIC') {
            window.location.href = '/medic/login';
        }

        this.getBloodTypes().subscribe((options) => {
            this.bloodTypes= options;
        });

        this.getPatientInfo();
    }

    formSubmit() {
        console.log(this.medicalHistoryModel);
        if (this.medicalHistoryModel.weight === '' || this.medicalHistoryModel.weight === null) {
            Swal.fire('Ingrese peso en kg', 'El peso es requisito.', 'warning');
            return;
        }
        if (this.medicalHistoryModel.height === '' || this.medicalHistoryModel.height === null) {
            Swal.fire('Ingrese su altura', 'La altura es requisito.', 'warning');
            return;
        }
        if (this.medicalHistoryModel.bloodType === '' || this.medicalHistoryModel.bloodType === null) {
            Swal.fire('Ingrese el grupo sanguíneo', 'El grupo sanguíneo es requisito.', 'warning');
            return;
        }

        // Check if medicalHistoryModel is defined
        if (!this.medicalHistoryModel) {
            Swal.fire('Error', 'No se proporcionó la historia médica.', 'error');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const createMedicalHistoryObservable = this.userService.createMedicalHistory(this.medicalHistoryModel);

        if (createMedicalHistoryObservable === undefined) {
            Swal.fire('Error', 'El método createMedicalHistory no devuelve un observable.', 'error');
            return;
        }

        createMedicalHistoryObservable.subscribe(
            (data) => {
                Swal.fire('Historia clínica guardada', 'Historia clínica guardada con éxito en el sistema.', 'success');
                this.router.navigate(['medic/attendPatient']);
            }, (error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erróneos.', 'error');
            }
        );
    }

    getBloodTypes():Observable<string[]> {
        return this.userService.getBloodTypes();
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
