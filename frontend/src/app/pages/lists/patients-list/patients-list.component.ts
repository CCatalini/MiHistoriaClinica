import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { PatientsListService } from '../../../services/medic/patients-list.service';

@Component({
    selector: 'app-patients-list',
    templateUrl: './patients-list.component.html',
    styleUrls: ['./patients-list.component.css']
})
export class PatientsListComponent implements OnInit {

    patient = {
        name: '',
        lastname: '',
        dni: '',
        email: '',
    };

    patients: any[] = [];

    constructor(private userService: PatientsListService, private router: Router) { }

    ngOnInit(): void {
        console.log('Hola');
        this.formSubmit();
    }

    formSubmit() {
        this.userService.getPatientsList().subscribe(
            (data: any) => {
                this.patients = data;
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
