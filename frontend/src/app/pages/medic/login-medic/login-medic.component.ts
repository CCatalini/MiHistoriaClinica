import {Component, OnInit} from '@angular/core';
import {LoginPatientService} from "../../../services/patient/login-patient.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import {LoginMedicService} from "../../../services/medic/login-medic.service";

@Component({
  selector: 'app-login-medic',
  templateUrl: './login-medic.component.html',
  styleUrls: ['./login-medic.component.css']
})
export class LoginMedicComponent implements OnInit{
    public medic = {
        matricula: '',
        password: '',
    }

    constructor(private userService:LoginMedicService, private snack:MatSnackBar){
    }

    ngOnInit(): void {
    }

    formSubmit(){
        console.log(this.medic);
        if(this.medic.matricula == '' || this.medic.matricula == null){
            Swal.fire('Ingrese su matrícula', 'El número de matrícula es requisito para iniciar sesión como médico.', 'warning');
            return;
        }
        if(this.medic.password == '' || this.medic.password == null){
            Swal.fire('Ingrese su contraseña', 'La contraseña es requisito para iniciar sesión como médico.', 'warning');
            return;
        }
        this.userService.loginMedic(this.medic).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Usuario guardado', 'Usuario registrado con éxito en el sistema.', 'success');
            }
        )
    }
}