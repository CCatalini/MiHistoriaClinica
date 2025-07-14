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
                    console.log('Medicines List:', data.map(medicine => ({ medicineId: medicine.medicineId, status: medicine.status })));
                    if (Array.isArray(data)) {
                        this.medicines = data;
                    } else {
                        Swal.fire('Error', 'La respuesta del servidor no contiene una lista de medicamentosválida (1).', 'error');
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
            this.patientService.getAnalysisList(token).subscribe(
                (data: any) => {
                    this.analysisList = data;
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
            this.patientService.getAppointmentsList(token).subscribe(
                (data: any) => {
                    console.log(data); // Agregar este console.log para verificar la respuesta del servidor
                    if (Array.isArray(data)) {
                        this.appointments = data;
                    } else {
                        Swal.fire('Error', 'La respuesta del servidor no contiene una lista de turnos válida.', 'error');
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
                    console.log('Medicines List:', data.map(medicine => ({ medicineId: medicine.medicineId, status: medicine.status })));
                    if (Array.isArray(data)) {
                        this.medicines = data;
                    } else {
                        Swal.fire('Error', 'La respuesta del servidor no contiene una lista de medicamentos válida (2).', 'error');
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
            this.patientService.getMedicinesByStatus(status).subscribe(
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
                    console.log('Analysis List:', data.map(analysis => ({ medicineId: analysis.analysis_id, status: analysis.status })));
                    if (Array.isArray(data)) {
                        this.analysisList = data;
                    } else {
                        Swal.fire('Error', 'La respuesta del servidor no contiene una lista de estudios válida.', 'error');
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
            this.patientService.getAnalysisByStatus(status).subscribe(
                (analysis: any[]) => {
                    this.analysisList = analysis;
                    console.log('Se ha filtrado con éxito');
                },
                (error: any) => {
                    console.log('Error al filtrar estudios:', error);
                }
            );
        }
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
