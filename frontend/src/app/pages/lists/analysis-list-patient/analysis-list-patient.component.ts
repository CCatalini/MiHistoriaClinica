import {Component, OnInit} from '@angular/core';
import {MedicService} from "../../../services/medic/medic.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: 'app-analysis-list-patient',
  templateUrl: './analysis-list-patient.component.html',
  styleUrls: ['./analysis-list-patient.component.css']
})
export class AnalysisListPatientComponent implements OnInit{
    analysis = {
        name: '',
        medicalCenter: '',
        description: '',
    };

    analysisList: any[] = [];

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }
    }

    formSubmit() {
        this.userService.getAnalysisList().subscribe(
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
    }
}
