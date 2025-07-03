import { Component, OnInit } from '@angular/core';
import { PatientService } from "../../../services/patient/patient.service";
import { Router } from "@angular/router";
import Swal from "sweetalert2";

@Component({
    selector: 'app-all-medics-list',
    templateUrl: './all-medics-list.component.html',
    styleUrls: ['./all-medics-list.component.css']
})
export class AllMedicsListComponent implements OnInit {
    medics: any[] = [];
    filteredMedics: any[] = [];
    specialties: string[] = [];
    selectedSpecialty: string = '';
    selectedName: string = '';
    names: string[] = [];

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Creamos medics list
            this.userService.getAllSpecialties().subscribe((data: any) => {
                this.specialties = data;
            });
        }
    }

    formSubmit() {
        this.userService.getAllMedicsList().subscribe(
            (data: any) => {
                if (Array.isArray(data)) {
                    this.medics = data;
                    this.filteredMedics = data;
                    this.updateSpecialties();
                    this.updateNames();
                } else {
                    Swal.fire('Error', 'La respuesta del servidor no contiene una lista de médicos válida.', 'error');
                }
            },
            (error: any) => {
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

    filterMedics() {
        this.filteredMedics = this.medics.filter(medic => {
            const specialtyMatch = this.selectedSpecialty ? medic.specialty === this.selectedSpecialty : true;
            const nameMatch = this.selectedName ? medic.name === this.selectedName : true;
            return specialtyMatch && nameMatch;
        });
        this.updateSpecialties();
        this.updateNames();
    }

    updateSpecialties() {
        // Especialidades presentes en la lista filtrada
        this.specialties = Array.from(new Set(this.medics
            .filter(medic => {
                const nameMatch = this.selectedName ? medic.name === this.selectedName : true;
                return nameMatch;
            })
            .map((m: any) => m.specialty)));
    }

    updateNames() {
        // Nombres presentes en la lista filtrada
        this.names = Array.from(new Set(this.medics
            .filter(medic => {
                const specialtyMatch = this.selectedSpecialty ? medic.specialty === this.selectedSpecialty : true;
                return specialtyMatch;
            })
            .map((m: any) => m.name)));
    }

    onSpecialtyChange() {
        this.selectedName = '';
        this.filterMedics();
    }

    onNameChange() {
        this.filterMedics();
    }

    redirectToAddTurno(medicId: string) {
        this.router.navigate(['/patient/add-turno'], { queryParams: { medicId: medicId } });
    }
}
