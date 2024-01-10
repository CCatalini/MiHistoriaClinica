import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import Swal from "sweetalert2";
import { PatientService } from "../../../services/patient/patient.service";

@Component({
    selector: 'app-add-turno',
    templateUrl: './add-turno.component.html',
    styleUrls: ['./add-turno.component.css']
})
export class AddTurnoComponent implements OnInit {
    public turnoDTO = {
        fechaTurno: '',
        horaTurno: '',
        medicalCenter: ''
    }

    public medicId!: string;

    constructor(private userService: PatientService, private router: Router, private activatedRoute: ActivatedRoute) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }

        this.activatedRoute.queryParams.subscribe(params => {
            this.medicId = params['medicId'];
        });
    }

    formSubmit() {
        console.log(this.turnoDTO);
        if (this.turnoDTO.fechaTurno == '' || this.turnoDTO.fechaTurno == null) {
            Swal.fire('Ingrese la fecha del turno', 'La fecha es requisito para agendar turno', 'warning');
            return;
        }
        if (this.turnoDTO.horaTurno == '' || this.turnoDTO.horaTurno == null) {
            Swal.fire('Ingrese el horario del turno', 'El horario es requisito para agendar turno', 'warning');
            return;
        }
        if (this.turnoDTO.medicalCenter == '' || this.turnoDTO.medicalCenter == null) {
            Swal.fire('Ingrese el centro médico', 'El centro médico es requisito para agendar turno', 'warning');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const addMedicalAppointmentObservable = this.userService.addTurno(this.turnoDTO, this.medicId);

        if (addMedicalAppointmentObservable === undefined) {
            Swal.fire('Error', 'El método addMedicalAppointment no devuelve un observable.', 'error');
            return;
        }

        addMedicalAppointmentObservable.subscribe(
            (data) => {
                Swal.fire('Consulta registrada', 'Consulta registrada con éxito en el sistema.', 'success');
                this.router.navigate(['patient/home']);
            },
            (error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erróneos.', 'error');
            }
        );
    }
}
