import { Component } from '@angular/core';
import Swal from "sweetalert2";
import {CreateMedicalHistoryService} from "../../../services/medicalHistory/medicalHistory.service";

@Component({
  selector: 'app-create-medical-history',
  templateUrl: './create-medical-history.component.html',
  styleUrls: ['./create-medical-history.component.css']
})
export class CreateMedicalHistoryComponent {
    public medicalHistory = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    }

    constructor(private userService:CreateMedicalHistoryService){
    }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }

    formSubmit(){
        console.log(this.medicalHistory);
        if(this.medicalHistory.weight == '' || this.medicalHistory.weight == null){
            Swal.fire('Ingrese peso en kg', 'El pes es requisito.', 'warning');
            return;
        }
        if(this.medicalHistory.height == '' || this.medicalHistory.height == null){
            Swal.fire('Ingrese su altura', 'La altura es requisito.', 'warning');
            return;
        }
        if(this.medicalHistory.bloodType == '' || this.medicalHistory.bloodType == null){
            Swal.fire('Ingrese el grupo sanguíneo', 'El grupo sanguíneo es requisito.', 'warning');
            return;
        }
        this.userService.createMedicalHistory(this.medicalHistory).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Historia clínica guardada', 'Historia clínica guardada con éxito en el sistema.', 'success');
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
