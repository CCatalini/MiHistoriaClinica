import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {PatientService} from "../../../services/patient/patient.service";

@Component({
  selector: 'app-appointments-list-patient',
  templateUrl: './appointments-list-patient.component.html',
  styleUrls: ['./appointments-list-patient.component.css']
})
export class AppointmentsListPatientComponent implements OnInit{
    appointments: any[] = [];

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
            this.userService.getAppointmentsList(token).subscribe(
                (data: any) => {
                    console.log(data);
                    this.appointments = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log(error);
                    // Solo mostrar error si es un error real del servidor (500+)
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.appointments = [];
                }
            );
        } else {
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }

    getEstadoLabel(estado: number): string {
        switch(estado) {
            case 0: return 'Pendiente';
            case 1: return 'Completada';
            case 2: return 'Cancelada';
            default: return 'Pendiente';
        }
    }

    getEstadoBadgeClass(estado: number): string {
        switch(estado) {
            case 0: return 'bg-warning text-dark';
            case 1: return 'bg-success';
            case 2: return 'bg-danger';
            default: return 'bg-warning text-dark';
        }
    }

    cambiarEstado(appointmentId: number, event: any) {
        const nuevoEstado = parseInt(event.target.value);
        const estadoNombre = this.getEstadoLabel(nuevoEstado);
        
        this.userService.updateAppointmentEstado(appointmentId, estadoNombre).subscribe(
            (response: any) => {
                Swal.fire('Éxito', 'Estado actualizado correctamente', 'success');
                this.formSubmit(); // Recargar la lista
            },
            (error: any) => {
                console.log(error);
                Swal.fire('Error', 'No se pudo actualizar el estado', 'error');
            }
        );
    }
}
