import {Component, OnInit} from '@angular/core';
import { Router } from "@angular/router";
import {PatientService} from "../../../services/patient/patient.service";
import Swal from "sweetalert2";
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpResponse} from "@angular/common/http";

@Component({
    selector: 'app-medical-history-list',
    templateUrl: './medical-history-list.component.html',
    styleUrls: ['./medical-history-list.component.css'],
})

export class MedicalHistoryListComponent implements OnInit {
    public medicalHistory: any = {
        weight: '',
        height: '',
        allergy: '',
        bloodType: '',
        chronicDisease: '',
        actualMedicine: '',
        familyMedicalHistory: ''
    };

    downloadList: FormGroup;

    medicines: any[] = [];
    analysisList: any[] = [];
    appointments: any[] = [];

    constructor(private patientService: PatientService, private router: Router, private formBuilder: FormBuilder) {
        this.downloadList = this.formBuilder.group({
            fichaMedica: false,
            medicamentos: false,
            estudios: false,
            consultasMedicas: false,
        });
    }

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit();
        }
    }

    private formSubmit(): void {
        this.patientService.getMedicalFile().subscribe(
            (response) => {
                this.medicalHistory = response;
            },
            (error) => {
                console.log('Error occurred while fetching medical history:', error);
            }
        );

        const token = localStorage.getItem('token');
        if (token) {
            this.patientService.getMedicinesList(token).subscribe(
                (data: any[]) => {
                    console.log('Medicines List:', data);
                    if (Array.isArray(data)) {
                        this.medicines = data;
                    } else {
                        this.medicines = [];
                    }
                },
                (error: any) => {
                    console.log('Error fetching medicines:', error);
                    // Solo mostrar error si es un error real del servidor (500), no si simplemente no hay datos
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.medicines = [];
                }
            );
            this.patientService.getAnalysisList(token).subscribe(
                (data: any) => {
                    this.analysisList = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log('Error fetching analysis:', error);
                    // Solo mostrar error si es un error real del servidor (500), no si simplemente no hay datos
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.analysisList = [];
                }
            );
            this.patientService.getAppointmentsList(token).subscribe(
                (data: any) => {
                    console.log('Appointments:', data);
                    this.appointments = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log('Error fetching appointments:', error);
                    // Solo mostrar error si es un error real del servidor (500), no si simplemente no hay datos
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.appointments = [];
                }
            );
        } else {
            // Handle the case where the token is not found
        }
    }

    updateMedicineStatus(medicineId: number, status: string) {
        this.patientService.updateMedicineStatus(medicineId, status).subscribe(
            () => {
                console.log('Estado del medicamento actualizado con éxito');
            },
            (error: any) => {
                console.log('Error al actualizar el estado del medicamento:', error);
            }
        );
    }

    getMedicinesByStatus(status: string) {
        if(status === "santi" ) {
            const token = localStorage.getItem('token')
            this.patientService.getMedicinesList(token!).subscribe(
                (data: any[]) => {
                    console.log('Medicines List:', data);
                    this.medicines = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log('Error fetching medicines:', error);
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.medicines = [];
                }
            );
        } else {
            this.patientService.getMedicinesByStatus(status).subscribe(
                (medicines: any[]) => {
                    this.medicines = Array.isArray(medicines) ? medicines : [];
                    console.log('Se ha filtrado con éxito');
                },
                (error: any) => {
                    console.log('Error al filtrar medicamentos:', error);
                    this.medicines = [];
                }
            );
        }
    }

    updateAnalysisStatus(analysis_id: number, status: string) {
        this.patientService.updateAnalysisStatus(analysis_id, status).subscribe(
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
            this.patientService.getAnalysisList(token!).subscribe(
                (data: any[]) => {
                    console.log('Analysis List:', data);
                    this.analysisList = Array.isArray(data) ? data : [];
                },
                (error: any) => {
                    console.log('Error fetching analysis:', error);
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.analysisList = [];
                }
            );
        } else {
            this.patientService.getAnalysisByStatus(status).subscribe(
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

    /* ---------------------------------------------
     *  Helpers: visibility of sections on the page
     * --------------------------------------------- */
    get hasSelection(): boolean {
        const { fichaMedica, medicamentos, estudios, consultasMedicas } = this.downloadList.value;
        return fichaMedica || medicamentos || estudios || consultasMedicas;
    }

    get showMedicalFile(): boolean {
        return !this.hasSelection || this.downloadList.get('fichaMedica')?.value;
    }

    get showMedicines(): boolean {
        return !this.hasSelection || this.downloadList.get('medicamentos')?.value;
    }

    get showAnalysis(): boolean {
        return !this.hasSelection || this.downloadList.get('estudios')?.value;
    }

    get showAppointments(): boolean {
        return !this.hasSelection || this.downloadList.get('consultasMedicas')?.value;
    }

    downloadMedicalHistory(): void {
        const token = localStorage.getItem('token');
        if (token) {
            const includeMedicalFile = this.downloadList.get('fichaMedica')?.value || false;
            const includeMedications = this.downloadList.get('medicamentos')?.value || false;
            const includeAnalysis = this.downloadList.get('estudios')?.value || false;
            const includeAppointments = this.downloadList.get('consultasMedicas')?.value || false;

            console.log(`includeMedicalFile: ${includeMedicalFile}, includeMedications: ${includeMedications}, includeAnalysis: ${includeAnalysis}, includeAppointments: ${includeAppointments}`);

            this.patientService.downloadMedicalHistory(token, includeMedicalFile, includeAnalysis, includeMedications, includeAppointments)
                .subscribe({
                    next: (response: HttpResponse<Blob>) => {
                        const blob = response.body!;

                        if (blob.size === 0) {
                            Swal.fire('Error', 'No se pudo generar el PDF. Intente nuevamente más tarde.', 'error');
                            return;
                        }

                        const url = window.URL.createObjectURL(blob);
                        const a = document.createElement('a');
                        a.href = url;
                        a.download = 'historia_clinica.pdf';
                        a.click();
                        window.URL.revokeObjectURL(url);
                    },
                    error: (error) => {
                        console.error('Error al descargar el historial médico:', error);
                        Swal.fire('Error', 'Error al descargar la historia clínica.', 'error');
                    }
                });
        }
    }

}
