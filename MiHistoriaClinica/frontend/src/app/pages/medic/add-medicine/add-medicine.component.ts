import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';
import { Router } from '@angular/router';
import {Observable} from "rxjs";

@Component({
    selector: 'app-alta-medicamento',
    templateUrl: './add-medicine.component.html',
    styleUrls: ['./add-medicine.component.css'],
})
export class AddMedicineComponent implements OnInit {
    public medicine = {
        medicineName: null,
        medicineDescription:'',
        comments: '',
        prescriptionDay:'',
        status: 'Pendiente',

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
        this.getMedicineOptions().subscribe(options => {
            if (options.length > 0) {
                // Inicializa medicine.medicineName con el primer elemento de la lista
                this.medicine.medicineName = options[0];
            }
        });

    }

    formSubmit() {
        console.log(this.medicine);
        if (this.medicine.medicineName === '' || this.medicine.medicineName === null) {
            Swal.fire(
                'Medicamento',
                'Seleccione un medicamento de la lista.',
                'warning'
            );
            return;
        }
        if (this.medicine.comments === '' || this.medicine.comments === null) {
            Swal.fire(
                'Comentarios: ',
                'El laboratorio es requisito para cargar el medicamento.',
                'warning'
            );
            return;
        }
        if (this.medicine.prescriptionDay === '' || this.medicine.prescriptionDay === null) {
            Swal.fire(
                'Ingrese la descripción',
                'La descripción es requisito para cargar el medicamento.',
                'warning'
            );
            return;
        }

        // Check if medicalHistoryModel is defined
        if (!this.medicine) {
            Swal.fire('Error', 'No se proporcionó el medicamento.', 'error');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const addMedicineObservable = this.userService.addMedicine(this.medicine);

        if (addMedicineObservable === undefined) {
            Swal.fire('Error', 'El método createMedicine no devuelve un observable.', 'error');
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

    getMedicineOptions(): Observable<any> {
        return this.userService.getMedicineOptions();  // Ajusta el nombre del método según tu servicio
    }
}
