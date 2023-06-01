import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list.component.html',
    styleUrls: ['./medicines-list.component.css']
})
export class MedicinesListComponent {
    medicine = {
        medicineName: '',
        activeIngredient: '',
        lab: '',
        description: '',
    };

    medicines: any[] = [];

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        this.formSubmit();
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
        // Aquí puedes implementar la lógica de redireccionamiento a la página de edición
        // Puedes utilizar el Router de Angular para navegar a una nueva ruta
        // Por ejemplo:
        this.router.navigate(['/medicines', medicine.id, 'edit']);
    }
}
