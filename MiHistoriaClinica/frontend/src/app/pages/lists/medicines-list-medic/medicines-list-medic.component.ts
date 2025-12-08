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
    patient: any;
    
    // Variables para tooltip flotante
    tooltipText: string | null = null;
    tooltipX: number = 0;
    tooltipY: number = 0;

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) { }

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/medic/login']);
        } else {
            this.formSubmit();
        }
        this.getPatientInfo();
    }

    formSubmit() {
        const createGetMedicinesListObservable = this.userService.getMedicinesList();
        if (createGetMedicinesListObservable === undefined) {
            Swal.fire('Error', 'No se pudo obtener la lista.', 'error');
            return;
        }
        createGetMedicinesListObservable.subscribe(
            (data: any) => {
                this.medicines = Array.isArray(data) ? data : [];
            },
            (error: any) => {
                console.log(error);
                if (error.status >= 500) {
                    Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                }
                this.medicines = [];
            }
        );
    }

    deleteMedicine(medicine: any) {
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
                        Swal.fire('Eliminado', 'El medicamento ha sido eliminado.', 'success');
                    },
                    (error) => {
                        Swal.fire('Error', 'No se pudo eliminar el medicamento.', 'error');
                    }
                );
            }
        });
    }

    getMedicinesByStatus(status: string) {
        if (status === "santi") {
            const getMedicinesListObservable = this.userService.getMedicinesList();
            if (!getMedicinesListObservable) {
                Swal.fire('Error', 'Error al obtener la lista.', 'error');
                return;
            }
            getMedicinesListObservable?.subscribe(
                (data: Object) => {
                    this.medicines = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log(error);
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.medicines = [];
                }
            );
        } else {
            this.userService.getMedicinesByStatus(status).subscribe(
                (medicines: any[]) => {
                    this.medicines = Array.isArray(medicines) ? medicines : [];
                },
                (error: any) => {
                    console.log('Error al filtrar medicamentos:', error);
                    this.medicines = [];
                }
            );
        }
    }

    showTooltip(event: MouseEvent, text: string | null): void {
        if (!text || text.length <= 60) return;
        
        const rect = (event.target as HTMLElement).getBoundingClientRect();
        this.tooltipX = rect.left;
        this.tooltipY = rect.bottom + window.scrollY + 5;
        this.tooltipText = text;
    }

    hideTooltip(): void {
        this.tooltipText = null;
    }

    formatMedicineName(name: string): string {
        if (!name) return '';
        const nameMap: { [key: string]: string } = {
            'IBUPROFENO': 'Ibuprofeno',
            'PARACETAMOL': 'Paracetamol',
            'AMOXICILINA': 'Amoxicilina',
            'OMEPRAZOL': 'Omeprazol',
            'LORATADINA': 'Loratadina',
            'METFORMINA': 'Metformina',
            'ENALAPRIL': 'Enalapril',
            'LOSARTAN': 'Losartán',
            'ASPIRINA': 'Aspirina',
            'DICLOFENAC': 'Diclofenac'
        };
        return nameMap[name] || name.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
    }

    getStatusClass(status: string): string {
        switch (status?.toLowerCase()) {
            case 'pendiente':
                return 'status-pending';
            case 'en curso':
                return 'status-in-progress';
            case 'finalizado':
                return 'status-completed';
            default:
                return '';
        }
    }

    getPatientInfo(): void {
        const patientLinkCode = localStorage.getItem('patientLinkCode');
        if (!patientLinkCode) {
            console.error('No hay paciente vinculado');
            return;
        }

        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        headers = headers.set('patientLinkCode', patientLinkCode);

        this.httpClient.get<any>('http://localhost:8080/medic/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
    }
}
