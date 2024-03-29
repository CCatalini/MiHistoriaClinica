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
                    console.log(data); // Agrego este console.log para verificar la respuesta del servidor
                    if (Array.isArray(data)) {
                        this.turnos = data;
                    } else {
                        Swal.fire('Error', 'La respuesta del servidor no contiene una lista de medicamentos válida.', 'error');
                    }
                },
                (error: any) => {
                    console.log(error);
                    if (error.status === 400) {
                        Swal.fire('Error', 'Existen datos erróneos.', 'error');
                    } else if (error.status === 404) {
                        Swal.fire('Error', 'No se encontraron pacientes.', 'error');
                    } else {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                }
            );
        } else {
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }

    deleteTurno(turno: any) {
        console.log(turno.turnoId); // Verificar el valor del ID del medicamento
        if (!turno.turnoId) {
            Swal.fire('Error', 'ID del medicamento no válido.', 'error');
            return;
        }
        Swal.fire({
            title: 'Eliminar medicamento',
            text: '¿Estás seguro de que quieres eliminar este medicamento?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.deleteTurno(turno.turnoId).subscribe(
                    () => {
                        const index = this.turnos.findIndex((m) => m.turnoId === turno.turnoId);
                        if (index !== -1) {
                            this.turnos.splice(index, 1);
                        }
                    },
                );
            }
        });
    }
}
