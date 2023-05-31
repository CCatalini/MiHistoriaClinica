import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {LinkPatientService} from "../../../services/medic/link-patient.service";

@Component({
  selector: 'app-link-patient',
  templateUrl: './link-patient.component.html',
  styleUrls: ['./link-patient.component.css']
})
export class LinkPatientComponent implements OnInit{
    public patient = {
        dni: '',
        codigo: '',
    }

    constructor(private userService:LinkPatientService, private router: Router){
    }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';

        }
    }

    formSubmit(){
        console.log(this.patient);
        if(this.patient.codigo == '' || this.patient.codigo == null){
            Swal.fire('Ingrese el código del paciente', 'El código del paciente es requisito para atenderlo.', 'warning');
            return;
        }
        this.userService.linkPatient(this.patient).subscribe(
            (data) => {
                console.log(data);
                this.router.navigate(['medic/attend-patient']);
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
