import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {PatientService} from "../../../services/patient/patient.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-medics-list',
  templateUrl: './medics-list.component.html',
  styleUrls: ['./medics-list.component.css']
})
export class MedicsListComponent implements OnInit{

    medicsList: any[] = [];
    specialtyOptions: string[] = ['Sin filtro'];

    public medic = {
        name: '',
        lastname: '',
        email: '',
        matricula: '',
        specialty: '',
    };

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        // Verifico usuario
        if (localStorage.getItem('userType') !== 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.getSpecialtyOptions().subscribe((options) => {
                this.specialtyOptions = ['Sin filtro'].concat(options);
            });
            this.formSubmit();
        }
    }


    formSubmit() {
        const token = localStorage.getItem('token');
        if(token) {
            this.userService.getMedicsList(token).subscribe(
                (data: any) => {
                    console.log(data); // Agregar este console.log para verificar la respuesta del servidor
                    if (Array.isArray(data)) {
                        this.medicsList = data;
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
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }

    getSpecialtyOptions(): Observable<string[]> {
        return this.userService.getAllSpecialties();
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

    getMedicsBySpecialty() {
        if (this.medic.specialty === 'Sin filtro') {
            // Si se selecciona 'Sin filtro', obtener todos los médicos
            this.formSubmit();
        } else {
            // Filtrar por especialidad
            console.log('Especialidad seleccionada: ', this.medic.specialty);
            this.userService.getMedicsBySpecialty(this.medic.specialty).subscribe(
                (medics: any[]) => {
                    this.medicsList = medics;
                    console.log('Se ha filtrado con éxito');
                },
                (error: any) => {
                    console.log('Error al filtrar médicos:', error);
                }
            );
        }
    }
}
