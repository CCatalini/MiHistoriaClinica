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
                this.router.navigate(['medic/home']);
            },(error) => {
                console.log(error);
                
                // Verificar si el error es por email no verificado
                const errorMessage = error.error?.message || error.message || '';
                
                if (errorMessage.includes('verificar tu email') || errorMessage.includes('email antes de iniciar')) {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Cuenta no verificada',
                        html: '<p>Aún no has verificado tu cuenta médica.</p>' +
                              '<p><strong>📧 Revisa tu correo electrónico</strong> y haz clic en el enlace de verificación.</p>' +
                              '<p style="font-size: 14px; color: #666;">Si no encuentras el email, revisa tu carpeta de spam.</p>',
                        confirmButtonText: 'Entendido',
                        confirmButtonColor: '#4A90E2'
                    });
                } else {
                    Swal.fire('Error', 'Credenciales incorrectas. Verifica tu matrícula y contraseña.', 'error');
                }
            }
        )
    }
}
