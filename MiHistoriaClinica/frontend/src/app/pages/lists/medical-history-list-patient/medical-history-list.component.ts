import {Component, OnInit, ViewChild} from '@angular/core';
import { Router } from "@angular/router";
import {PatientService} from "../../../services/patient/patient.service";
import Swal from "sweetalert2";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";

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

    constructor(private patientService: PatientService, private router: Router, private formBuilder: FormBuilder) {
        this.downloadList = this.formBuilder.group({
            fichaMedica: false,
            medicamentos: false,
            estudios: false,
        });
    }

    ngOnInit(): void {
        if (localStorage.getItem('userType') !== 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Fetch medical history data
        }

        this.downloadList.valueChanges.subscribe(() => {
            this.logSelectedOptions();
        });
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
        // Código para llamar el método
    }

    logSelectedOptions() {
        const downloadListForm = this.downloadList.value;
        const selectedOptions = Object.keys(downloadListForm).filter(option => downloadListForm[option]);
        console.log('Opciones de descarga seleccionadas:', selectedOptions);
    }

}
