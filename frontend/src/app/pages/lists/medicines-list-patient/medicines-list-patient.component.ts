import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from "../../../services/medic/medic.service";

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-patient.component.html',
    styleUrls: ['./medicines-list-patient.component.css']
})
export class MedicinesListPatientComponent implements OnInit {
    medicines: any[] = [];

    constructor(private userService: MedicService, private router: Router) { }

    updateStatus(medicine: any) {
        // Buscar el medicamento correspondiente en la lista
        const foundMedicine = this.medicines.find(m => m.id === medicine.id);
        if (foundMedicine) {
            // Actualizar el estado del medicamento
            foundMedicine.status = medicine.status;
        }
    }

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

}
