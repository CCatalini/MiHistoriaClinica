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
}
