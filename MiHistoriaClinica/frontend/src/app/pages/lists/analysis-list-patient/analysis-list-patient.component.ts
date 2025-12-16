import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {PatientService} from "../../../services/patient/patient.service";

@Component({
  selector: 'app-analysis-list-patient',
  templateUrl: './analysis-list-patient.component.html',
  styleUrls: ['./analysis-list-patient.component.css']
})
export class AnalysisListPatientComponent implements OnInit{
    analysisList: any[] = [];
    
    // Variables para tooltip flotante
    tooltipText: string | null = null;
    tooltipX: number = 0;
    tooltipY: number = 0;

    constructor(private userService: PatientService, private router: Router) {}

    /**
     * Navega a reservar turno para un análisis específico
     */
    reserveSlotForAnalysis(analysis: any): void {
        this.router.navigate(['/patient/reserve-analysis-slot'], {
            queryParams: {
                analysisId: analysis.analysis_id,
                analysisType: this.formatAnalysisName(analysis.name),
                analysisEnum: analysis.name
            }
        });
    }

    /**
     * Reprogramar un estudio - navega a la pantalla de reserva
     */
    reprogramAnalysis(analysis: any): void {
        Swal.fire({
            title: 'Reprogramar estudio',
            text: '¿Deseas reprogramar este estudio? Se cancelará el turno actual.',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí, reprogramar',
            cancelButtonText: 'No',
            confirmButtonColor: '#3fb5eb'
        }).then((result) => {
            if (result.isConfirmed) {
                // Primero cancelar el turno actual
                this.userService.cancelStudySchedule(analysis.analysis_id).subscribe(
                    () => {
                        // Luego navegar a reservar nuevo turno
                        this.router.navigate(['/patient/reserve-analysis-slot'], {
                            queryParams: {
                                analysisId: analysis.analysis_id,
                                analysisType: this.formatAnalysisName(analysis.name),
                                analysisEnum: analysis.name
                            }
                        });
                    },
                    (error) => {
                        console.error('Error al cancelar:', error);
                        Swal.fire('Error', 'No se pudo cancelar el turno actual', 'error');
                    }
                );
            }
        });
    }

    /**
     * Cancelar un estudio programado
     */
    cancelAnalysis(analysis: any): void {
        Swal.fire({
            title: 'Cancelar turno',
            text: '¿Estás seguro de que deseas cancelar este turno?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, cancelar',
            cancelButtonText: 'No',
            confirmButtonColor: '#dc3545'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.cancelStudySchedule(analysis.analysis_id).subscribe(
                    () => {
                        Swal.fire({
                            title: 'Turno cancelado',
                            text: 'El turno ha sido cancelado. Puedes reservar uno nuevo cuando lo desees.',
                            icon: 'success',
                            confirmButtonColor: '#3fb5eb'
                        });
                        // Recargar la lista
                        this.formSubmit();
                    },
                    (error) => {
                        console.error('Error al cancelar:', error);
                        Swal.fire('Error', 'No se pudo cancelar el turno', 'error');
                    }
                );
            }
        });
    }

    /**
     * Retorna la clase CSS según el estado
     */
    getStatusClass(status: string): string {
        switch (status) {
            case 'Pendiente': return 'status-pending';
            case 'Programado': return 'status-scheduled';
            case 'En curso': return 'status-in-progress';
            case 'Finalizado': return 'status-completed';
            default: return '';
        }
    }

    ngOnInit(): void {
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }else {
            this.formSubmit();
        }
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if (token) {
            this.userService.getAnalysisList(token).subscribe(
                (data: any) => {
                    this.analysisList = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log(error);
                    // Solo mostrar error si es un error real del servidor (500+)
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.analysisList = [];
                }
            );
        } else {
            // Handle the case where the token is not found
        }
    }

    updateAnalysisStatus(analysis_id: number, status: string) {
        this.userService.updateAnalysisStatus(analysis_id, status).subscribe(
            () => {
                console.log('Estado del medicamento actualizado con éxito');
                // Realizar lógica adicional después de actualizar el estado del medicamento, si es necesario
            },
            (error: any) => {
                console.log('Error al actualizar el estado del medicamento:', error);
                // Manejar el error, mostrar mensajes de error, etc.
            }
        );
    }

    getAnalysisByStatus(status: string) {
        if(status === "santi" ) {
            const token = localStorage.getItem('token')
            this.userService.getAnalysisList(token!).subscribe(
                (data: any[]) => {
                    console.log('Analysis List:', data.map(analysis => ({ medicineId: analysis.analysis_id, status: analysis.status })));
                    this.analysisList = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log(error);
                    // Solo mostrar error si es un error real del servidor (500+)
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
                    console.log('Se ha filtrado con éxito');
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
        // Agregar T12:00:00 para evitar problemas de zona horaria
        const date = new Date(dateString + 'T12:00:00');
        const options: Intl.DateTimeFormatOptions = { 
            weekday: 'long', 
            day: 'numeric', 
            month: 'long', 
            year: 'numeric' 
        };
        return date.toLocaleDateString('es-ES', options);
    }

    formatAnalysisName(enumName: string): string {
        const names: {[key: string]: string} = {
            'HEMOGRAMA_COMPLETO': 'Hemograma Completo',
            'PERFIL_LIPIDICO': 'Perfil Lipídico',
            'GLUCEMIA_EN_AYUNAS': 'Glucemia en Ayunas',
            'PRUEBA_DE_EMABARAZO': 'Prueba de Embarazo',
            'ANALISIS_DE_ORINA': 'Análisis de Orina',
            'ELECTROCARDIOGRAMA': 'Electrocardiograma',
            'RADIOGRAFIA_DE_TORAX': 'Radiografía de Tórax',
            'ECOGRAFIA_ABDOMINAL': 'Ecografía Abdominal',
            'DENSITOMETRIA_OSEA': 'Densitometría Ósea',
            'ANALISIS_DE_TIROIDES': 'Análisis de Tiroides',
            'TOMOGRAFIA_COMPUTARIZADA': 'Tomografía Computarizada',
            'RESONANCIA_MAGNETICA': 'Resonancia Magnética',
            'COLONOSCOPIA': 'Colonoscopía',
            'MAMOGRAFIA': 'Mamografía',
            'TEST_DE_VIH': 'Test de VIH',
            'ANALISIS_DE_HEPATITIS': 'Análisis de Hepatitis',
            'HOLTER_CARDIACO': 'Holter Cardíaco',
            'ANALISIS_DE_ALERGIAS': 'Análisis de Alergias',
            'PAPANICOLAOU': 'Papanicolau',
            'TEST_DE_GLUCOSA_POSPRANDIAL': 'Test de Glucosa Posprandial',
            'CITOLOGIA_DE_ESPUTO': 'Citología de Esputo'
        };
        return names[enumName] || enumName;
    }
}
