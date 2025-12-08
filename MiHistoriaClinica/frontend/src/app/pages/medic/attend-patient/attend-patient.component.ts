import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { MedicService } from "../../../services/medic/medic.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-attend-patient',
    templateUrl: './attend-patient.component.html',
    styleUrls: ['./attend-patient.component.css']
})
export class AttendPatientComponent implements OnInit {
    patientId: string = '';
    patient: any;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private httpClient: HttpClient,
        private medicService: MedicService
    ) { }

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'MEDIC') {
            window.location.href = '/medic/login';
        } else {
            this.route.params.subscribe(params => {
                this.patientId = params['linkCode'];
            });
            // Limpiar datos de consulta anterior
            this.clearConsultationData();
            this.getPatientInfo();
        }
    }

    clearConsultationData(): void {
        localStorage.removeItem('estudiosAgregados');
        localStorage.removeItem('medicamentosAgregados');
        localStorage.removeItem('historiaActualizada');
    }

    /**
     * Finaliza la consulta y envía email de resumen al paciente
     */
    finishConsultation(): void {
        const patientLinkCode = localStorage.getItem('patientLinkCode');
        
        if (!patientLinkCode) {
            this.clearConsultationData();
            localStorage.setItem('patientLinkCode', '');
            this.router.navigate(['/medic/home']);
            return;
        }

        // Obtener cambios realizados
        const estudios = JSON.parse(localStorage.getItem('estudiosAgregados') || '[]');
        const medicamentos = JSON.parse(localStorage.getItem('medicamentosAgregados') || '[]');
        const historiaActualizada = localStorage.getItem('historiaActualizada') === 'true';

        this.medicService.finishConsultation(patientLinkCode, {
            estudios,
            medicamentos,
            historiaActualizada
        }).subscribe(
            () => {
                this.clearConsultationData();
                localStorage.setItem('patientLinkCode', '');
                Swal.fire({
                    title: 'Consulta Finalizada',
                    text: 'Se ha enviado un email de resumen al paciente.',
                    icon: 'success',
                    confirmButtonColor: '#3fb5eb'
                }).then(() => {
                    this.router.navigate(['/medic/home']);
                });
            },
            (error) => {
                console.error('Error enviando email:', error);
                this.clearConsultationData();
                localStorage.setItem('patientLinkCode', '');
                this.router.navigate(['/medic/home']);
            }
        );
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
