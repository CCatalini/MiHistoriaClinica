import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";
import Swal from "sweetalert2";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-appointments-list-medic',
  templateUrl: './appointments-list-medic.component.html',
  styleUrls: ['./appointments-list-medic.component.css']
})
export class AppointmentsListMedicComponent implements OnInit{

    appointments: any[] = [];
    patient: any;

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) { }

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Creamos medics list
        }

        this.getPatientInfo();
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if(token) {
            this.userService.getAppointmentsList().subscribe(
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

    getPatientInfo(): void {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        this.httpClient.get<any>('http://localhost:8080/patient/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
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
