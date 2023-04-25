import {Component, OnInit} from '@angular/core';
import Swal from "sweetalert2";
import {LoginPatientService} from "../../../services/patient/login-patient.service";

@Component({
  selector: 'app-login-patient',
  templateUrl: './login-patient.component.html',
  styleUrls: ['./login-patient.component.css']
})
export class LoginPatientComponent implements OnInit{

    public patient = {
        dni: '',
        password: '',
    }

    constructor(private userService:LoginPatientService){

    }

    ngOnInit(): void {
    }

    formSubmit(){
        console.log(this.patient);
        if(this.patient.dni == '' || this.patient.dni == null){
            Swal.fire('Ingrese su DNI', 'El DNI es requisito para iniciar sesión como paciente.', 'warning');
            return;
        }
        if(this.patient.password == '' || this.patient.password == null){
            Swal.fire('Ingrese su contraseña', 'La contraseña es requisito para iniciar sesión como paciente.', 'warning');
            return;
        }
        this.userService.loginPatient(this.patient).subscribe(
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
