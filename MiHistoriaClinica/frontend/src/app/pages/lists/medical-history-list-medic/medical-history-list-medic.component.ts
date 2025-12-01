import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import Swal from 'sweetalert2';

@Component({
    selector: 'app-medical-history-list-medic',
    templateUrl: './medical-history-list-medic.component.html',
    styleUrls: ['./medical-history-list-medic.component.css']
})
export class MedicalHistoryListMedicComponent implements OnInit{
    public medicalHistory: any = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    };

    patient : any;

    constructor(private medicService: MedicService, private router: Router, private httpClient: HttpClient) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/patient/login']);
        } else {
            this.fetchMedicalHistory(); // Fetch medical history data
        }

        this.getPatientInfo();
    }

    private fetchMedicalHistory(): void {
        this.medicService.getPatientMedicalHistory().subscribe(
            (response) => {
                this.medicalHistory = response;
            },
            (error) => {
                console.log('Error occurred while fetching medical history:', error);
            }
        );
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

    downloadPatientHistory(): void {
        this.medicService.downloadPatientMedicalHistory().subscribe({
            next: (response: HttpResponse<Blob>) => {
                const blob = response.body!;

                if (blob.size === 0) {
                    Swal.fire('Error', 'No se pudo generar el PDF. Intente nuevamente más tarde.', 'error');
                    return;
                }

                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                const patientName = this.patient ? `${this.patient.name}_${this.patient.lastname}` : 'paciente';
                a.download = `historia_clinica_${patientName}.pdf`;
                a.click();
                window.URL.revokeObjectURL(url);
                Swal.fire('Éxito', 'Historia clínica descargada correctamente', 'success');
            },
            error: (error) => {
                console.error('Error al descargar el historial médico:', error);
                Swal.fire('Error', 'Error al descargar la historia clínica del paciente.', 'error');
            }
        });
    }

}
