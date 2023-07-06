import {Component, OnInit} from '@angular/core';
import {MedicService} from "../../../services/medic/medic.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: 'app-edit-medical-history',
  templateUrl: './edit-medical-history.component.html',
  styleUrls: ['./edit-medical-history.component.css']
})
export class EditMedicalHistoryComponent implements OnInit{
    public medicalHistoryModel = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    }

    public patient = {
        code: '',
    };

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'MEDIC') {
            window.location.href = '/medic/login';
        }
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

        const updateMedicalHistoryObservable = this.userService.updateMedicalHistory(this.medicalHistoryModel);

        if (updateMedicalHistoryObservable === undefined) {
            Swal.fire('Error', 'El método createMedicalHistory no devuelve un observable.', 'error');
            return;
        }

        updateMedicalHistoryObservable.subscribe(
            (data) => {
                Swal.fire('Historia clínica guardada', 'Historia clínica guardada con éxito en el sistema.', 'success');
                this.router.navigate(['medic/attendPatient']);
            }, (error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erróneos.', 'error');
            }
        );
    }
}

