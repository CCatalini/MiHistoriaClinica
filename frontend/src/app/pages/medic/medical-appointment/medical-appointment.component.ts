import { Component } from '@angular/core';
import {CreateMedicalHistoryService} from "../../../services/medicalHistory/medicalHistory.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-medical-appointment.service.sepc.ts',
  templateUrl: './medical-appointment.component.html',
  styleUrls: ['./medical-appointment.component.css']
})
export class MedicalAppointmentComponent {
    public medicalAppointment = {
        appointmentReason: '',
        currentIllness: '',
        physicalExam: '',
        observations: '',
    }

    constructor(private userService:CreateMedicalHistoryService){
    }

    ngOnInit(): void {
    }

    formSubmit(){
        console.log(this.medicalAppointment);
        if(this.medicalAppointment.appointmentReason == '' || this.medicalAppointment.appointmentReason == null){
            Swal.fire('Ingrese peso en kg', 'El pes es requisito.', 'warning');
            return;
        }
        if(this.medicalAppointment.currentIllness == '' || this.medicalAppointment.currentIllness == null){
            Swal.fire('Ingrese su altura', 'La altura es requisito.', 'warning');
            return;
        }
        if(this.medicalAppointment.physicalExam == '' || this.medicalAppointment.physicalExam == null){
            Swal.fire('Ingrese el grupo sanguíneo', 'El grupo sanguíneo es requisito.', 'warning');
            return;
        }
        this.userService.createMedicalHistory(this.medicalAppointment).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Consulta médica guardada', 'Consulta médica guardada con éxito en el sistema.', 'success');
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}