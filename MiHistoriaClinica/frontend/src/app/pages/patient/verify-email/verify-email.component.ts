import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../../../services/patient/patient.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {
  
  verifying: boolean = true;
  success: boolean = false;
  message: string = 'Verificando tu email...';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    // Obtener el token de la URL
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      
      if (token) {
        this.verifyEmail(token);
      } else {
        this.verifying = false;
        this.success = false;
        this.message = 'Token de verificación no encontrado';
      }
    });
  }

  verifyEmail(token: string): void {
    this.patientService.verifyEmail(token).subscribe({
      next: (response) => {
        this.verifying = false;
        this.success = true;
        this.message = '¡Email verificado exitosamente!';
        
        Swal.fire({
          title: '¡Éxito!',
          text: 'Tu email ha sido verificado. Ya puedes iniciar sesión.',
          icon: 'success',
          confirmButtonText: 'Ir al Login'
        }).then(() => {
          this.router.navigate(['/patient/login']);
        });
      },
      error: (error) => {
        this.verifying = false;
        this.success = false;
        this.message = error.error || 'Error al verificar el email';
        
        Swal.fire({
          title: 'Error',
          text: this.message,
          icon: 'error',
          confirmButtonText: 'Volver'
        }).then(() => {
          this.router.navigate(['/']);
        });
      }
    });
  }
}
