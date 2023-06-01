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
        dni: '',
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
