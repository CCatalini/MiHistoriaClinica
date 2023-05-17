import { Component, OnInit } from '@angular/core';
import Swal from "sweetalert2";
import { Router } from "@angular/router";
import { PatientsListService } from "../../../services/medic/patients-list.service";

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
        birthday: ''
    };

    patients: ArrayBuffer | any = null;

    constructor(private userService: PatientsListService, private router: Router) { }

    ngOnInit(): void {
        console.log("Hola");
    }

    formSubmit() {
        this.userService.getPatientsList().subscribe(
            (data: any) => {
                this.patients = data;
            },
            (error: any) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        );
    }


}
