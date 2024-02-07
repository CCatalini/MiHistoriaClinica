import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import Swal from "sweetalert2";


@Component({
    selector: 'app-confirmation',
    template: `
        <div *ngIf="loading">Cargando...</div>
        <div *ngIf="confirmationMessage">{{ confirmationMessage }}</div>
    `,
})
export class ConfirmationComponent implements OnInit {
    loading = true;
    confirmationMessage = '';

    constructor(private route: ActivatedRoute, private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        const token = this.authService.getTokenFromLocalStorage();

        if (token) {
            this.confirmAccount(token);
        } else {
            this.loading = false;
            this.confirmationMessage = 'Token no válido.';
        }
    }

    confirmAccount(token: string): void {
        this.authService.confirmAccount(token).subscribe(
            () => {
                this.loading = false;
                this.confirmationMessage = '¡Cuenta confirmada con éxito!';
                setTimeout(() => {
                    this.router.navigate(['/patient/login']);
                }, 3000);
            },
            (error: any) => {
                this.loading = false;
                this.confirmationMessage = 'Error al confirmar la cuenta.';
            }
        );
    }
}
