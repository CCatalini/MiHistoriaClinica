import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PatientService } from "../../../services/patient/patient.service";
import { HttpClient } from "@angular/common/http";

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-patient.component.html',
    styleUrls: ['./medicines-list-patient.component.css']
})
export class MedicinesListPatientComponent implements OnInit {
    medicines: any[] = [];

    constructor(
        private patientService: PatientService,
        private router: Router,
        private http: HttpClient
    ) { }

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Fetch medicines list
        }
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if (token) {
            this.patientService.getMedicinesList(token).subscribe(
                (data: any[]) => {
                    console.log('Medicines List:', data.map(medicine => ({ medicineId: medicine.medicineId, status: medicine.status })));
                    if (Array.isArray(data)) {
                        this.medicines = data;
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
            // Handle the case where the token is not found
        }
    }

    updateMedicineStatus(medicineId: number, status: string) {
        this.patientService.updateMedicineStatus(medicineId, status).subscribe(
            () => {
                console.log('Estado del medicamento actualizado con éxito');
                // Realizar lógica adicional después de actualizar el estado del medicamento, si es necesario
            },
            (error: any) => {
                console.log('Error al actualizar el estado del medicamento:', error);
                // Manejar el error, mostrar mensajes de error, etc.
            }
        );
    }

    getMedicinesByStatus(status: string) {

        if(status === "santi" ) {
            const token = localStorage.getItem('token')
            this.patientService.getMedicinesList(token!).subscribe(
                (data: any[]) => {
                    console.log('Medicines List:', data.map(medicine => ({ medicineId: medicine.medicineId, status: medicine.status })));
                    if (Array.isArray(data)) {
                        this.medicines = data;
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
            // Handle the case where the token is not found
            this.patientService.getMedicinesByStatus(status).subscribe(
                (medicines: any[]) => {
                    this.medicines = medicines;
                    console.log('Se ha filtrado con éxito');
                },
                (error: any) => {
                    console.log('Error al filtrar medicamentos:', error);
                }
            );
        }
    }

}

