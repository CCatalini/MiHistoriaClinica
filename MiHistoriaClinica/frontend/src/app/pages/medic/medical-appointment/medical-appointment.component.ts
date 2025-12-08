import { Component } from '@angular/core';
import Swal from "sweetalert2";
import {MedicService} from "../../../services/medic/medic.service";
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MatFormFieldModule} from '@angular/material/form-field'; 

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

    patient : any;

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }

        this.getPatientInfo();
    }
    formSubmit(){
        console.log(this.medicalAppointment);
        if(this.medicalAppointment.appointmentReason == '' || this.medicalAppointment.appointmentReason == null){
            Swal.fire('Ingrese el motivo de consulta', 'El motivo de consulta es obligatorio.', 'warning');
            return;
        }
        if(this.medicalAppointment.currentIllness == '' || this.medicalAppointment.currentIllness == null){
            Swal.fire('Ingrese la enfermedad actual', 'La enfermedad actual es obligatoria.', 'warning');
            return;
        }
        if(this.medicalAppointment.physicalExam == '' || this.medicalAppointment.physicalExam == null){
            Swal.fire('Ingrese el examen físico', 'El examen físico es obligatorio.', 'warning');
            return;
        }

        // Check if medicalAppointmentModel is defined
        if (!this.medicalAppointment) {
            Swal.fire('Error', 'No se proporcionó la historia médica.', 'error');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const addMedicalAppointmentObservable = this.userService.addMedicalAppointment(this.medicalAppointment);

        if (addMedicalAppointmentObservable === undefined) {
            Swal.fire('Error', 'El método addMedicalAppointment no devuelve un observable.', 'error');
            return;
        }

        addMedicalAppointmentObservable.subscribe(
            (data) => {
                Swal.fire('Consulta registrada', 'Consulta registrada con éxito en el sistema.', 'success');
                this.router.navigate(['medic/attendPatient']);
            },
            (error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erróneos.', 'error');
            }
        );
    }

    getPatientInfo(): void {
        const patientLinkCode = localStorage.getItem('patientLinkCode');
        if (!patientLinkCode) {
            console.error('No hay paciente vinculado');
            return;
        }

        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        headers = headers.set('patientLinkCode', patientLinkCode);

        this.httpClient.get<any>('http://localhost:8080/medic/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
    }
}
