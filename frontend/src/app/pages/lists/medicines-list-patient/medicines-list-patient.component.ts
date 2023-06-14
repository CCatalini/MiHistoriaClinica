import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from "../../../services/medic/medic.service";
import {PatientService} from "../../../services/patient/patient.service";
import {HttpClient} from "@angular/common/http";
import {EMPTY} from "rxjs";

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-patient.component.html',
    styleUrls: ['./medicines-list-patient.component.css']
})
export class MedicinesListPatientComponent implements OnInit {
    medicines: any[] = [];

    constructor(private userService: PatientService, private router: Router, private patientService: PatientService,private http: HttpClient) { }

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
                (data: any) => {
                    console.log(data); // Agregar este console.log para verificar la respuesta del servidor
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
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }

    updateMedicineStatus(medicineId: number, status: string) {
        const token = localStorage.getItem('token');
        const url = `http://localhost:8080/patient/update-medicine-status/?medicineId=${medicineId}&status=${status}`;
        const body = { status: status };

        if (token) {
            return this.http.put(url, body, {
                headers: { Authorization: `Bearer ${token}` }
            }).subscribe(
                (response: any) => {
                    console.log(response); // Verificar la respuesta del backend
                    // Realizar cualquier acción adicional necesaria después de guardar el estado del medicamento
                },
                (error: any) => {
                    console.log(error); // Verificar el error del backend
                    // Manejar el error adecuadamente, por ejemplo, mostrar un mensaje de error al usuario
                }
            );
        } else {
            console.log('Token not found');
            // Manejar el caso en el que no se encuentre el token en el local storage
            return null; // Agregar esta línea para devolver un valor en este caso
        }
    }



}
