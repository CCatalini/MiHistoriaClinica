import { Component, OnInit } from '@angular/core';
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import Swal from 'sweetalert2';
import {SignupPatientService} from "../../../services/patient/signup-patient.service";
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

    constructor(private userService:SignupPatientService, private snack:MatSnackBar){

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
          },(error) => {
              console.log(error);
              Swal.fire('Error', 'Existen datos erroneos.', 'error');
          }
      )
    }
}
