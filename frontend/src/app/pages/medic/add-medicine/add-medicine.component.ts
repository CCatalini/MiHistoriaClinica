import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-alta-medicamento',
    templateUrl: './add-medicine.component.html',
    styleUrls: ['./add-medicine.component.css'],
})
export class AddMedicineComponent implements OnInit {
    public medicine = {
        medicineName: '',
        activeIngredient: '',
        description: '',
        lab: '',
        status: '',
    };

    public patient = {
        code: '',
    };

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        // Verifico usuario
        if (localStorage.getItem('userType') !== 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }

    formSubmit() {
        console.log(this.medicine);
        if (this.medicine.medicineName === '' || this.medicine.medicineName === null) {
            Swal.fire(
                'Ingrese el nombre del medicamento',
                'El nombre es requisito para cargar el medicamento.',
                'warning'
            );
            return;
        }
        if (this.medicine.activeIngredient === '' || this.medicine.activeIngredient === null) {
            Swal.fire(
                'Ingrese el compuesto activo',
                'El compuesto activo es requisito para cargar el medicamento.',
                'warning'
            );
            return;
        }
        if (this.medicine.lab === '' || this.medicine.lab === null) {
            Swal.fire(
                'Ingrese el laboratorio',
                'El laboratorio es requisito para cargar el medicamento.',
                'warning'
            );
            return;
        }
        if (this.medicine.description === '' || this.medicine.description === null) {
            Swal.fire(
                'Ingrese la descripción',
                'La descripción es requisito para cargar el medicamento.',
                'warning'
            );
            return;
        }

        // Check if medicalHistoryModel is defined
        if (!this.medicine) {
            Swal.fire('Error', 'No se proporcionó la historia médica.', 'error');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const addMedicineObservable = this.userService.addMedicine(this.medicine);

        if (addMedicineObservable === undefined) {
            Swal.fire('Error', 'El método createMedicalHistory no devuelve un observable.', 'error');
            return;
        }

        addMedicineObservable.subscribe(
            (data) => {
                Swal.fire('Medicamento registrado', 'Medicamento registrado con éxito en el sistema.', 'success');
                this.router.navigate(['medic/attendPatient']);
            },
            (error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erróneos.', 'error');
            }
        );
    }
}
