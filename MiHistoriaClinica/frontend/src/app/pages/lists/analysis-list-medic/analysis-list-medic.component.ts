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

    constructor(private userService: MedicService, private router: Router, private httpClient: HttpClient) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/patient/login';
        }else {
            this.formSubmit(); // Fetch medicines list
        }

        this.getPatientInfo();
    }

    formSubmit() {
        const createGetAnalysisListObservable = this.userService.getAnalysisList();
        if (createGetAnalysisListObservable === undefined) {
            Swal.fire('Error', 'El método createMedicalHistory no devuelve un observable.', 'error');
            return;
        }
        createGetAnalysisListObservable.subscribe(
            (data: any) => {
                console.log(data); // Agregar este console.log para verificar la respuesta del servidor
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
    }

    deleteAnalysis(analysis: any) {
        if (!analysis.analysis_id) {
            Swal.fire('Error', 'ID del estudio no válido.', 'error');
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
                this.userService.deleteAnalysis(analysis.analysis_id).subscribe(
                    () => {
                        const index = this.analysisList.findIndex((m) => m.analysis_id === analysis.analysis_id);
                        if (index !== -1) {
                            this.analysisList.splice(index, 1);
                        }
                    },
                );
            }
        });
    }

    getAnalysisByStatus(status: string) {
        if (status === "santi") {
            const getAnalysisListObservable = this.userService.getAnalysisList();
            if (!getAnalysisListObservable) {
                Swal.fire('Error', 'El método getMedicinesList no devuelve un observable.', 'error');
                return;
            }
            getAnalysisListObservable?.subscribe(
                (data: Object) => { // Change the type of 'data' to 'Object'
                    console.log('Analysis List:', data);
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
            this.userService.getAnalysisByStatus(status).subscribe(
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
