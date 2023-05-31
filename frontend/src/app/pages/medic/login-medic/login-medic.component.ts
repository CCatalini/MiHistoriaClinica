import {Component, OnInit} from '@angular/core';
import Swal from "sweetalert2";
import { Router } from '@angular/router';
import {MedicService} from "../../../services/medic/medic.service";

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

    constructor(private userService:MedicService, private router: Router){
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
            (data: any) => {
                console.log(data);
                localStorage.setItem('token', data.token);
                localStorage.setItem('userType', 'MEDIC')
                this.router.navigate(['home-medic']);
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
