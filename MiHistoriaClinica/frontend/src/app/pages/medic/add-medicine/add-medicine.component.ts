import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';
import { Router } from '@angular/router';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {formatDate} from "@angular/common";

@Component({
    selector: 'app-alta-medicamento',
    templateUrl: './add-medicine.component.html',
    styleUrls: ['./add-medicine.component.css'],
})
export class AddMedicineComponent implements OnInit {
    public medicine = {
        name: '',
        description: '',
        comments: '',
        prescriptionDay: '',  // Ajusta el tipo de datos aquí
        status: 'Pendiente',
    };

    medicineOptions: string[] = [];

    patient: any;

    constructor(private userService: MedicService,
                private router: Router,
                private httpClient: HttpClient) {}

    ngOnInit(): void {
        // Verifico usuario
        if (localStorage.getItem('userType') !== 'MEDIC') {
            window.location.href = '/medic/login';
        }

        this.getMedicineOptions().subscribe((options) => {
            this.medicineOptions = options;
        });

        this.medicine.prescriptionDay = formatDate(this.medicine.prescriptionDay, 'yyyy-MM-dd', 'en-US');

        this.getPatientInfo();

    }


    formSubmit() {
        console.log(this.medicine);
        if (this.medicine.name === '' || this.medicine.name === null) {
            Swal.fire(
                'Medicamento',
                'Seleccione un medicamento de la lista.',
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

    getMedicineOptions(): Observable<string[]> {
        return this.userService.getAllMedicineNames();
    }

    onMedicineSelectionChange(): void {
        const selectedMedicineName = this.medicine.name;
        if (selectedMedicineName) {
            // Llama al backend para obtener la descripción
            this.userService.getMedicineDescription(selectedMedicineName).subscribe(
                (description) => {
                    this.medicine.description = description;
                },
                (error) => {
                    console.error('Error fetching medicine description:', error);
                    // Agregar manejo de errores aquí, por ejemplo, mostrar un mensaje al usuario
                    // this.showErrorMessage('No se pudo obtener la descripción del medicamento.');
                }
            );
        } else {
            this.medicine.description = '';
        }
    }




    getPatientInfo(): void {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        this.httpClient.get<any>('http://localhost:8080/patient/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
    }
}
