import {Component, OnInit} from '@angular/core';
import {PatientService} from "../../../services/patient/patient.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: 'app-turnos-list',
  templateUrl: './turnos-list.component.html',
  styleUrls: ['./turnos-list.component.css']
})
export class TurnosListComponent implements OnInit{
    turnos: any[] = [];

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Creamos medics list
        }
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if(token) {
            this.userService.getTurnosList(token).subscribe(
                (data: any) => {
                    console.log('Turnos:', data);
                    this.turnos = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log('Error fetching turnos:', error);
                    // Solo mostrar error si es un error real del servidor (500), no si simplemente no hay datos
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.turnos = [];
                }
            );
        } else {
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }

    deleteTurno(turno: any) {
        console.log(turno.turnoId); // Verificar el valor del ID del turno
        if (!turno.turnoId) {
            Swal.fire({
                title: 'Error',
                text: 'ID del turno no válido.',
                icon: 'error',
                confirmButtonColor: '#3fb5eb'
            });
            return;
        }
        Swal.fire({
            title: 'Cancelar Turno Médico',
            text: '¿Estás seguro de que quieres cancelar este turno médico?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3fb5eb',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, cancelar turno',
            cancelButtonText: 'No, mantener turno'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.deleteTurno(turno.turnoId).subscribe(
                    () => {
                        const index = this.turnos.findIndex((m) => m.turnoId === turno.turnoId);
                        if (index !== -1) {
                            this.turnos.splice(index, 1);
                        }
                        Swal.fire({
                            title: '¡Turno Cancelado!',
                            text: 'Tu turno médico ha sido cancelado exitosamente.',
                            icon: 'success',
                            confirmButtonColor: '#3fb5eb'
                        });
                    },
                    (error) => {
                        console.error('Error al cancelar turno:', error);
                        Swal.fire({
                            title: 'Error',
                            text: 'No se pudo cancelar el turno. Por favor, intenta nuevamente.',
                            icon: 'error',
                            confirmButtonColor: '#3fb5eb'
                        });
                    }
                );
            }
        });
    }
}
