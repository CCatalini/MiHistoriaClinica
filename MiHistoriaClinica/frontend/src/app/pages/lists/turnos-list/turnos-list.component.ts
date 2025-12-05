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

    formatDate(dateStr: string): string {
        if (!dateStr) return '';
        const date = new Date(dateStr + 'T12:00:00');
        const days = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];
        const months = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
        return `${days[date.getDay()]} ${date.getDate()} ${months[date.getMonth()]}`;
    }

    formatMedicalCenter(center: string): string {
        if (!center) return '';
        // Convertir SEDE_PRINCIPAL_HOSPITAL_AUSTRAL a "Sede Principal - Hospital Austral"
        return center
            .split('_')
            .map((word, index, arr) => {
                // Capitalizar primera letra, resto en minúscula
                const formatted = word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
                // Agregar guión antes de "Hospital" o palabras clave similares
                if (word.toLowerCase() === 'hospital' || word.toLowerCase() === 'clinica' || word.toLowerCase() === 'centro') {
                    return '- ' + formatted;
                }
                return formatted;
            })
            .join(' ')
            .replace('  ', ' ');
    }

    deleteTurno(turno: any) {
        console.log(turno.turnoId);
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
