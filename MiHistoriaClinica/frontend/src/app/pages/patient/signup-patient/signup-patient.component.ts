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
              Swal.fire({
                  title: '¡Registro exitoso!',
                  html: '<p>Usuario registrado con éxito.</p>' +
                        '<p><strong>📧 Revisa tu correo electrónico</strong> para verificar tu cuenta antes de iniciar sesión.</p>' +
                        '<p>Si no ves el email, revisa tu carpeta de spam.</p>',
                  icon: 'success',
                  confirmButtonText: 'Entendido'
              });
              this.router.navigate(['/']);
          },(error) => {
              console.log(error);
              Swal.fire('Error', 'Existen datos erroneos.', 'error');
          }
      )
    }
}
