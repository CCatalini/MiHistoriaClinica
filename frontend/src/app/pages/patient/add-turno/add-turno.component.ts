import {Component, OnInit} from '@angular/core';
import {MedicService} from "../../../services/medic/medic.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-turno',
  templateUrl: './add-turno.component.html',
  styleUrls: ['./add-turno.component.css']
})
export class AddTurnoComponent implements OnInit{
    public turno = {
        fechaTurno: '',
        horaTurno: '',
    }

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }
    formSubmit(){
        console.log(this.turno);
        if(this.turno.fechaTurno == '' || this.turno.fechaTurno == null){
            Swal.fire('Ingrese peso en kg', 'El pes es requisito.', 'warning');
            return;
        }
        if(this.turno.horaTurno == '' || this.turno.horaTurno == null){
            Swal.fire('Ingrese su altura', 'La altura es requisito.', 'warning');
            return;
        }


        // Check if medicalAppointmentModel is defined
        if (!this.turno) {
            Swal.fire('Error', 'No se proporcionó la historia médica.', 'error');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const addMedicalAppointmentObservable = this.userService.addMedicalAppointment(this.turno);

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
}
