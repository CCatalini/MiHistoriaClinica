import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { MedicService } from "../../../services/medic/medic.service";
import Swal from "sweetalert2";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Component({
    selector: 'app-appointments-list-medic',
    templateUrl: './appointments-list-medic.component.html',
    styleUrls: ['./appointments-list-medic.component.css']
})
export class AppointmentsListMedicComponent implements OnInit {

    appointments: any[] = [];
    filteredAppointments: any[] = [];
    patient: any;
    isLoading: boolean = false;
    
    // Filtros
    selectedEstado: string = '1'; // Por defecto: Completadas
    searchTerm: string = '';

    constructor(
        private userService: MedicService, 
        private router: Router, 
        private httpClient: HttpClient
    ) { }

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/medic/login']);
        } else {
            this.loadAppointments();
            this.getPatientInfo();
        }
    }

    loadAppointments() {
        const token = localStorage.getItem('token');
        if (token) {
            this.isLoading = true;
            this.userService.getAppointmentsList().subscribe(
                (data: any) => {
                    this.appointments = Array.isArray(data) ? data : [];
                    this.filterAppointments();
                    this.isLoading = false;
                },
                (error: any) => {
                    console.log(error);
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.appointments = [];
                    this.filteredAppointments = [];
                    this.isLoading = false;
                }
            );
        }
    }

    filterAppointments() {
        let result = [...this.appointments];
        
        // Filtrar por estado
        if (this.selectedEstado !== '') {
            const estadoNum = parseInt(this.selectedEstado);
            result = result.filter(a => a.estado === estadoNum);
        }
        
        // Filtrar por término de búsqueda
        if (this.searchTerm.trim()) {
            const term = this.searchTerm.toLowerCase().trim();
            result = result.filter(a => 
                (a.appointmentReason && a.appointmentReason.toLowerCase().includes(term)) ||
                (a.currentIllness && a.currentIllness.toLowerCase().includes(term)) ||
                (a.observations && a.observations.toLowerCase().includes(term))
            );
        }
        
        this.filteredAppointments = result;
    }

    getPatientInfo(): void {
        const linkCode = localStorage.getItem('patientLinkCode');
        if (!linkCode) return;
        
        let headers = new HttpHeaders().set('patientLinkCode', linkCode);
        
        this.httpClient.get<any>('http://localhost:8080/medic/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
    }

    getEstadoLabel(estado: number): string {
        switch (estado) {
            case 0: return 'Pendiente';
            case 1: return 'Completada';
            case 2: return 'Cancelada';
            default: return 'Pendiente';
        }
    }

    getEstadoBadgeClass(estado: number): string {
        switch (estado) {
            case 0: return 'badge-warning';
            case 1: return 'badge-success';
            case 2: return 'badge-danger';
            default: return 'badge-warning';
        }
    }

    cambiarEstado(appointmentId: number, event: any) {
        const nuevoEstado = parseInt(event.target.value);
        const estadoNombre = this.getEstadoLabel(nuevoEstado);
        
        this.userService.updateAppointmentEstado(appointmentId, estadoNombre).subscribe(
            (response: any) => {
                Swal.fire({
                    title: 'Estado Actualizado',
                    text: 'El estado de la consulta ha sido actualizado correctamente',
                    icon: 'success',
                    confirmButtonColor: '#3fb5eb'
                });
                this.loadAppointments();
            },
            (error: any) => {
                console.log(error);
                Swal.fire({
                    title: 'Error',
                    text: 'No se pudo actualizar el estado',
                    icon: 'error',
                    confirmButtonColor: '#3fb5eb'
                });
            }
        );
    }
}
