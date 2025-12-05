import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { MedicService } from "../../../services/medic/medic.service";

@Component({
    selector: 'app-patients-list',
    templateUrl: './patients-list.component.html',
    styleUrls: ['./patients-list.component.css']
})
export class PatientsListComponent implements OnInit {

    patients: any[] = [];
    filteredPatients: any[] = [];
    searchTerm: string = '';
    isLoading: boolean = false;

    constructor(private userService: MedicService, private router: Router) { }

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/medic/login']);
        } else {
            this.loadPatients();
        }
    }

    loadPatients() {
        const token = localStorage.getItem('token');
        if (token) {
            this.isLoading = true;
            this.userService.getPatientsList(token).subscribe(
                (data: any) => {
                    this.patients = Array.isArray(data) ? data : [];
                    this.filteredPatients = [...this.patients];
                    this.isLoading = false;
                },
                (error: any) => {
                    console.log(error);
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.patients = [];
                    this.filteredPatients = [];
                    this.isLoading = false;
                }
            );
        }
    }

    filterPatients() {
        if (!this.searchTerm.trim()) {
            this.filteredPatients = [...this.patients];
            return;
        }

        const term = this.searchTerm.toLowerCase().trim();
        this.filteredPatients = this.patients.filter(patient => {
            const fullName = `${patient.name} ${patient.lastname}`.toLowerCase();
            return fullName.includes(term) || 
                   patient.name.toLowerCase().includes(term) || 
                   patient.lastname.toLowerCase().includes(term);
        });
    }

    getInitials(name: string, lastname: string): string {
        const first = name ? name.charAt(0).toUpperCase() : '';
        const last = lastname ? lastname.charAt(0).toUpperCase() : '';
        return first + last;
    }

    verHistoriaClinica(patient: any) {
        if (patient.linkCode) {
            localStorage.setItem('patientLinkCode', patient.linkCode);
            this.router.navigate(['/medic/medicalHistoryList']);
        } else {
            // Si no hay linkCode, intentar generar uno o pedir al paciente que lo genere
            Swal.fire({
                title: 'Acceso a Historia Clínica',
                text: `Para acceder a la historia clínica de ${patient.name} ${patient.lastname}, el paciente debe generar un código de vinculación desde su portal.`,
                icon: 'info',
                confirmButtonColor: '#3fb5eb'
            });
        }
    }

    verConsultas(patient: any) {
        if (patient.linkCode) {
            localStorage.setItem('patientLinkCode', patient.linkCode);
            this.router.navigate(['/medic/appointmentList']);
        } else {
            Swal.fire({
                title: 'Acceso a Consultas',
                text: `Para ver las consultas de ${patient.name} ${patient.lastname}, el paciente debe generar un código de vinculación desde su portal.`,
                icon: 'info',
                confirmButtonColor: '#3fb5eb'
            });
        }
    }
}
