import {Component, OnInit} from '@angular/core';
import Swal from "sweetalert2";
import { Router } from '@angular/router';
import {PatientService} from "../../../services/patient/patient.service";

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

    constructor(private userService:PatientService, private router: Router){
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
            (data: any) => {
                console.log(data);
                localStorage.setItem('token', data.token);
                localStorage.setItem('userType', 'PATIENT')
                this.router.navigate(['patient/home']);
            },(error) => {
                console.log(error);
                
                // Verificar si el error es por email no verificado
                const errorMessage = error.error?.message || error.message || '';
                
                if (errorMessage.includes('verificar tu email') || errorMessage.includes('email antes de iniciar')) {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Cuenta no verificada',
                        html: '<p>Aún no has verificado tu cuenta.</p>' +
                              '<p><strong>📧 Revisa tu correo electrónico</strong> y haz clic en el enlace de verificación.</p>' +
                              '<p style="font-size: 14px; color: #666;">Si no encuentras el email, revisa tu carpeta de spam.</p>',
                        confirmButtonText: 'Entendido',
                        confirmButtonColor: '#4A90E2'
                    });
                } else {
                    Swal.fire('Error', 'Credenciales incorrectas. Verifica tu DNI y contraseña.', 'error');
                }
            }
        )
    }
}