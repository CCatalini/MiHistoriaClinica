import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { MedicService } from "../../../services/medic/medic.service";
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import Swal from 'sweetalert2';

@Component({
    selector: 'app-medical-history-list-medic',
    templateUrl: './medical-history-list-medic.component.html',
    styleUrls: ['./medical-history-list-medic.component.css']
})
export class MedicalHistoryListMedicComponent implements OnInit {
    public medicalHistory: any = null;
    patient: any = null;
    isLoading: boolean = false;

    constructor(
        private medicService: MedicService, 
        private router: Router, 
        private httpClient: HttpClient
    ) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/medic/login']);
        } else {
            this.loadData();
        }
    }

    loadData(): void {
        this.isLoading = true;
        this.getPatientInfo();
        this.fetchMedicalHistory();
    }

    private fetchMedicalHistory(): void {
        this.medicService.getPatientMedicalHistory().subscribe(
            (response) => {
                this.medicalHistory = response;
                this.isLoading = false;
            },
            (error) => {
                console.log('Error occurred while fetching medical history:', error);
                this.medicalHistory = null;
                this.isLoading = false;
            }
        );
    }

    getPatientInfo(): void {
        const linkCode = localStorage.getItem('patientLinkCode');
        if (!linkCode) return;
        
        let headers = new HttpHeaders().set('patientLinkCode', linkCode);
        
        this.httpClient.get<any>('http://localhost:8080/medic/get-patient-info', { headers }).subscribe(
            (response: any) => {
                this.patient = response;
            },
            (error: any) => {
                console.error('Error fetching patient info:', error);
            }
        );
    }

    downloadPatientHistory(): void {
        this.medicService.downloadPatientMedicalHistory().subscribe({
            next: (response: HttpResponse<Blob>) => {
                const blob = response.body!;

                if (blob.size === 0) {
                    Swal.fire({
                        title: 'Error',
                        text: 'No se pudo generar el PDF. Intente nuevamente más tarde.',
                        icon: 'error',
                        confirmButtonColor: '#3fb5eb'
                    });
                    return;
                }

                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                const patientName = this.patient ? `${this.patient.name}_${this.patient.lastname}` : 'paciente';
                a.download = `historia_clinica_${patientName}.pdf`;
                a.click();
                window.URL.revokeObjectURL(url);
                
                Swal.fire({
                    title: 'Descarga Exitosa',
                    text: 'Historia clínica descargada correctamente',
                    icon: 'success',
                    confirmButtonColor: '#3fb5eb'
                });
            },
            error: (error) => {
                console.error('Error al descargar el historial médico:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Error al descargar la historia clínica del paciente.',
                    icon: 'error',
                    confirmButtonColor: '#3fb5eb'
                });
            }
        });
    }
}
