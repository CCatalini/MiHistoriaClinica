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
    medicine = {
        medicineName: '',
        activeIngredient: '',
        lab: '',
        description: ''
    };

    medicines: any[] = [];

    constructor(private medicService: MedicService, private router: Router) { }

    ngOnInit(): void {
        // Verify user
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Fetch medicines list
        }
    }

    formSubmit() {
        this.medicService.getMedicinesList().subscribe(
            (data: any) => {
                this.medicines = data;
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
    }

    // todo falta el medicService.updateStatus() del back, cambiar el html cuando esté (dejé comentada la línea)
    /*updateStatus(medicine: any) {
        this.medicService.updateStatus(medicine.id, medicine.status).subscribe(
            (response: any) => {
                // Manejar la respuesta del backend si es necesario
                console.log('Estado actualizado correctamente');
            },
            (error: any) => {
                console.log(error);
                Swal.fire('Error', 'Se produjo un error al actualizar el estado.', 'error');
            }
        );
    }*/
}
