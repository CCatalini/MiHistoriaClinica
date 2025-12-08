import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";
import Swal from "sweetalert2";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-analysis-list-medic',
  templateUrl: './analysis-list-medic.component.html',
  styleUrls: ['./analysis-list-medic.component.css']
})
export class AnalysisListMedicComponent implements OnInit{
    analysisList: any[] = [];
    patient: any;
    
    // Variables para tooltip flotante
    tooltipText: string | null = null;
    tooltipX: number = 0;
    tooltipY: number = 0;

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) {}

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        } else {
            this.formSubmit();
        }
        this.getPatientInfo();
    }

    formSubmit() {
        const createGetAnalysisListObservable = this.userService.getAnalysisList();
        if (createGetAnalysisListObservable === undefined) {
            Swal.fire('Error', 'El método no devuelve un observable.', 'error');
            return;
        }
        createGetAnalysisListObservable.subscribe(
            (data: any) => {
                console.log(data);
                this.analysisList = Array.isArray(data) ? data : [];
            },
            (error: any) => {
                console.log(error);
                if (error.status >= 500) {
                    Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                }
                this.analysisList = [];
            }
        );
    }

    deleteAnalysis(analysis: any) {
        if (!analysis.analysis_id) {
            Swal.fire('Error', 'ID del estudio no válido.', 'error');
            return;
        }
        Swal.fire({
            title: 'Eliminar estudio',
            text: '¿Estás seguro de que quieres eliminar este estudio?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.deleteAnalysis(analysis.analysis_id).subscribe(
                    () => {
                        const index = this.analysisList.findIndex((m) => m.analysis_id === analysis.analysis_id);
                        if (index !== -1) {
                            this.analysisList.splice(index, 1);
                        }
                        Swal.fire('Eliminado', 'El estudio ha sido eliminado.', 'success');
                    },
                    (error) => {
                        Swal.fire('Error', 'No se pudo eliminar el estudio.', 'error');
                    }
                );
            }
        });
    }

    getAnalysisByStatus(status: string) {
        if (status === "santi") {
            const getAnalysisListObservable = this.userService.getAnalysisList();
            if (!getAnalysisListObservable) {
                Swal.fire('Error', 'Error al obtener la lista.', 'error');
                return;
            }
            getAnalysisListObservable?.subscribe(
                (data: Object) => {
                    this.analysisList = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log(error);
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.analysisList = [];
                }
            );
        } else {
            this.userService.getAnalysisByStatus(status).subscribe(
                (analysis: any[]) => {
                    this.analysisList = Array.isArray(analysis) ? analysis : [];
                },
                (error: any) => {
                    console.log('Error al filtrar estudios:', error);
                    this.analysisList = [];
                }
            );
        }
    }

    showTooltip(event: MouseEvent, text: string | null): void {
        if (!text || text.length <= 80) return;
        
        const rect = (event.target as HTMLElement).getBoundingClientRect();
        this.tooltipX = rect.left;
        this.tooltipY = rect.bottom + window.scrollY + 5;
        this.tooltipText = text;
    }

    hideTooltip(): void {
        this.tooltipText = null;
    }

    formatDate(dateString: string): string {
        if (!dateString) return '';
        const date = new Date(dateString);
        const options: Intl.DateTimeFormatOptions = { 
            weekday: 'long', 
            day: 'numeric', 
            month: 'long', 
            year: 'numeric' 
        };
        return date.toLocaleDateString('es-ES', options);
    }

    formatAnalysisName(name: string): string {
        if (!name) return '';
        // Convertir de SNAKE_CASE a formato legible
        const nameMap: { [key: string]: string } = {
            'HEMOGRAMA_COMPLETO': 'Hemograma Completo',
            'PERFIL_LIPIDICO': 'Perfil Lipídico',
            'GLUCEMIA': 'Glucemia',
            'ORINA_COMPLETA': 'Orina Completa',
            'HEPATOGRAMA': 'Hepatograma',
            'FUNCION_RENAL': 'Función Renal',
            'ELECTROCARDIOGRAMA': 'Electrocardiograma',
            'RADIOGRAFIA_TORAX': 'Radiografía de Tórax',
            'ECOGRAFIA_ABDOMINAL': 'Ecografía Abdominal',
            'TOMOGRAFIA': 'Tomografía',
            'RESONANCIA_MAGNETICA': 'Resonancia Magnética'
        };
        return nameMap[name] || name.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
    }

    getStatusClass(status: string): string {
        switch (status?.toLowerCase()) {
            case 'pendiente':
                return 'status-pending';
            case 'programado':
                return 'status-scheduled';
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
