import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MedicService } from '../../../services/medic/medic.service';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
    selector: 'app-medicines-list',
    templateUrl: './medicines-list-medic.component.html',
    styleUrls: ['./medicines-list-medic.component.css']
})
export class MedicinesListMedicComponent implements OnInit {
    medicines: any[] = [];
    private createGetMedicinesListObservable: boolean = false;
    patient : any;

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) { }

    ngOnInit(): void {
        // Verify user
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/medic/login']);
        } else {
            this.formSubmit(); // Fetch medicines list
        }

        this.getPatientInfo();
    }

    formSubmit() {
        const createGetMedicinesListObservable = this.userService.getMedicinesList();
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

    deleteMedicine(medicine: any) {
        console.log(medicine.medicineId); // Verificar el valor del ID del medicamento
        if (!medicine.medicineId) {
            Swal.fire('Error', 'ID del medicamento no válido.', 'error');
            return;
        }
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
                this.userService.deleteMedicine(medicine.medicineId).subscribe(
                    () => {
                        const index = this.medicines.findIndex((m) => m.medicineId === medicine.medicineId);
                        if (index !== -1) {
                            this.medicines.splice(index, 1);
                        }
                    },
                );
            }
        });
    }

    getMedicinesByStatus(status: string) {
        if (status === "santi") {
            const getMedicinesListObservable = this.userService.getMedicinesList();
            if (!getMedicinesListObservable) {
                Swal.fire('Error', 'El método getMedicinesList no devuelve un observable.', 'error');
                return;
            }
            getMedicinesListObservable?.subscribe(
                (data: Object) => { // Change the type of 'data' to 'Object'
                    console.log('Medicines List:', data);
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
            this.userService.getMedicinesByStatus(status).subscribe(
                (medicines: any[]) => {
                    this.medicines = medicines;
                    console.log('Se ha filtrado con éxito');
                },
                (error: any) => {
                    console.log('Error al filtrar medicamentos:', error);
                }
            );
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
