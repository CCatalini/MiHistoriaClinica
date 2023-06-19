import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';
import {PatientService} from "../../../services/patient/patient.service";

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-medic.component.html',
    styleUrls: ['./medicines-list-medic.component.css']
})
export class MedicinesListMedicComponent implements OnInit{
    medicines: any[] = [];
    private createGetMedicinesListObservable: boolean = false;

    constructor(private userService: MedicService, private router: Router) { }

    ngOnInit(): void {
        // Verify user
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/medic/login']);
        } else {
            this.formSubmit(); // Fetch medicines list
        }
    }

    formSubmit() {
        const patientLinkCode = localStorage.getItem('patientLinkCode');
        const createGetMedicinesListObservable = this.userService.getMedicinesList(patientLinkCode || '');
        if (createGetMedicinesListObservable === undefined) {
            Swal.fire('Error', 'El método createMedicalHistory no devuelve un observable.', 'error');
            return;
        }
        createGetMedicinesListObservable.subscribe(
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
    }

    /*editMedicine(medicine: any) {
        // Redirecciona a la página de edición del medicamento
        this.router.navigate(['/medic/medicines', medicine.id, 'edit']);
        // todo no me lleva al edit de medicamentos
    }*/

    deleteMedicine(medicine: any) {
        console.log(medicine.id); // Verificar el valor del ID del medicamento
        if (!medicine.id) {
            Swal.fire('Error', 'ID del medicamento no válido.', 'error');
            return;
        }
        // Aquí debes agregar la lógica para eliminar el medicamento de la lista
        // Por ejemplo, puedes mostrar un cuadro de diálogo de confirmación antes de eliminarlo
        Swal.fire({
            title: 'Eliminar medicamento',
            text: '¿Estás seguro de que quieres eliminar este medicamento?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.deleteMedicine(medicine.id).subscribe(
                    () => {
                        const index = this.medicines.findIndex((m) => m.id === medicine.id);
                        if (index !== -1) {
                            this.medicines.splice(index, 1);
                        }
                        Swal.fire('Eliminado', 'El medicamento ha sido eliminado correctamente.', 'success');
                    },
                    (error: any) => {
                        Swal.fire('Error', 'Se produjo un error al eliminar el medicamento.', 'error');
                    }
                );
            }
        });
    }
}
