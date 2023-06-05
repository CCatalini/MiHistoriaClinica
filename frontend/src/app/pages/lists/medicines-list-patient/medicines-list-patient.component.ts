import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-patient.component.html',
    styleUrls: ['./medicines-list-patient.component.css']
})
export class MedicinesListPatientComponent {
    medicine = {
        medicineName: '',
        activeIngredient: '',
        lab: '',
        description: '',
    };

    medicines: any[] = [];

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }
    }

    formSubmit() {
        this.userService.getMedicinesList().subscribe(
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
}
