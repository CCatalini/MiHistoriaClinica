import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {PatientService} from "../../../services/patient/patient.service";

@Component({
  selector: 'app-medics-list',
  templateUrl: './medics-list.component.html',
  styleUrls: ['./medics-list.component.css']
})
export class MedicsListComponent implements OnInit{
    medic = {
        name: '',
        lastname: '',
        dni: '',
        email: '',
    };

    medics: any[] = [];

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        console.log('Hola');
        this.formSubmit();
    }

    formSubmit() {
        this.userService.getMedicsList().subscribe(
            (data: any) => {
                this.medics = data;
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
}
