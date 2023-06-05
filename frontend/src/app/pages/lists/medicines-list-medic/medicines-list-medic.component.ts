import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-medic.component.html',
    styleUrls: ['./medicines-list-medic.component.css']
})
export class MedicinesListMedicComponent {
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
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
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

    editMedicine(medicine: any) {
        // Redirecciona a la página de edición del medicamento
        this.router.navigate(['/medic/medicines', medicine.id, 'edit']);
        // todo no me lleva al edit de medicamentos
    }
}
