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

    constructor(private userService: PatientService, private router: Router) {}

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
            // Handle the case where the token is not found
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
}
