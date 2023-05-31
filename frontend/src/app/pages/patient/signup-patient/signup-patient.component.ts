import { Component, OnInit } from '@angular/core';
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import {PatientService} from "../../../services/patient/patient.service";

@Component({
  selector: 'app-signup-patient',
  templateUrl: './signup-patient.component.html',
  styleUrls: ['./signup-patient.component.css']
})
export class SignupPatientComponent implements OnInit{

    public patient = {
        name: '',
        lastname: '',
        dni: '',
        email: '',
        password: '',
        birthday: '',
    }

    constructor(private userService:PatientService, private snack:MatSnackBar, private router: Router){

    }

    ngOnInit():void{
        console.log("Hola");
    }

    formSubmit(){
      console.log(this.patient);
      if(this.patient.dni == '' || this.patient.dni == null){
          Swal.fire('Ingrese su DNI', 'El DNI es requisito para registrarse como paciente.', 'warning');
          return;
      }
      this.userService.addPatient(this.patient).subscribe(
          (data) => {
              console.log(data);
              Swal.fire('Usuario guardado', 'Usuario registrado con éxito en el sistema.', 'success');
              this.router.navigate(['patient/login']);
          },(error) => {
              console.log(error);
              Swal.fire('Error', 'Existen datos erroneos.', 'error');
          }
      )
    }
}
