import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicService } from '../../../services/medic/medic.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-medic-verify-email',
  templateUrl: './verify-email.component.html',
  standalone: true,
  imports: [CommonModule],
  styleUrls: ['./verify-email.component.css']
})
export class MedicVerifyEmailComponent implements OnInit {
  
  verifying: boolean = true;
  success: boolean = false;
  message: string = 'Verificando tu email...';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private medicService: MedicService
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
    this.medicService.verifyEmail(token).subscribe({
      next: (response) => {
        this.verifying = false;
        this.success = true;
        this.message = '¡Email verificado exitosamente!';
        
        Swal.fire({
          title: '¡Éxito!',
          text: 'Tu email ha sido verificado. Ya puedes iniciar sesión como médico.',
          icon: 'success',
          confirmButtonText: 'Ir al Login'
        }).then(() => {
          this.router.navigate(['/medic/login']);
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
