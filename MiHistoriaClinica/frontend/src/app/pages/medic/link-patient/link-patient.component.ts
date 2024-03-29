import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {MedicService} from "../../../services/medic/medic.service";

@Component({
  selector: 'app-link-patient',
  templateUrl: './link-patient.component.html',
  styleUrls: ['./link-patient.component.css']
})
export class LinkPatientComponent implements OnInit{
    public patient = {
        code: '',
    }

    constructor(private userService:MedicService, private router: Router){
    }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }

    formSubmit(){
        console.log(this.patient);
        if(this.patient.code == '' || this.patient.code == null){
            Swal.fire('Ingrese el código del paciente', 'El código del paciente es requisito para atenderlo.', 'warning');
            return;
        }
        this.userService.linkPatient(this.patient.code).subscribe(
            (data) => {
                console.log(data);
                localStorage.setItem('patientLinkCode', this.patient.code);
                this.router.navigate(['medic/attendPatient']);
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'El código del paciente es incorrecto.', 'error');
            }
        )
    }
}
