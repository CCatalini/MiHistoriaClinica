import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-analysis-list-medic',
  templateUrl: './analysis-list-medic.component.html',
  styleUrls: ['./analysis-list-medic.component.css']
})
export class AnalysisListMedicComponent implements OnInit{
    analysisList: any[] = [];

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/patient/login';
        }else {
            this.formSubmit(); // Fetch medicines list
        }
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
    }
}
