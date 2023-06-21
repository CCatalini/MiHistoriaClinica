import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from "../../../services/medic/medic.service";
import { PatientService } from "../../../services/patient/patient.service";
import { HttpClient } from "@angular/common/http";
import { EMPTY } from "rxjs";

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-patient.component.html',
    styleUrls: ['./medicines-list-patient.component.css']
})
export class MedicinesListPatientComponent implements OnInit {
    medicines: any[] = [];

    constructor(
        private userService: PatientService,
        private router: Router,
        private patientService: PatientService,
        private http: HttpClient
    ) { }

    ngOnInit(): void {
        // Verify user
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Fetch medicines list
        }
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if (token) {
            this.userService.getMedicinesList(token).subscribe(
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


    updateMedicineStatus(medicineId: number, status: string): void {
        console.log('status:', status);

        const token = localStorage.getItem('token');
        const url = `http://localhost:8080/patient/update-medicine-status?medicineId=${medicineId}&status=${status}`;
        const body = { status: status };

        if (token) {
            this.http.put(url, body, {
                headers: { Authorization: `Bearer ${token}` }
            }).subscribe(
                (response: any) => {
                    console.log(response); // Verify the backend response
                    // Perform any additional actions required after saving the medicine status
                },
                (error: any) => {
                    console.log(error); // Verify the backend error
                    // Handle the error appropriately, for example, display an error message to the user
                }
            );
        } else {
            console.log('Token not found');
            // Handle the case where the token is not found
        }
    }
}
