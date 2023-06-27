import { Component } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import {MedicService} from "../../../services/medic/medic.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-analysis',
  templateUrl: './add-analysis.component.html',
  styleUrls: ['./add-analysis.component.css']
})
export class AddAnalysisComponent {
    public analysis = {
        name: '',
        medicalCenter: '',
        description: '',
    }

    constructor(private userService: MedicService, private router: Router) {}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }

    formSubmit(){
        console.log(this.analysis);
        if(this.analysis.name == '' || this.analysis.name == null){
            Swal.fire('Ingrese el nombre del estudio', 'El nombre es requisito para cargar el estudio.', 'warning');
            return;
        }
        if(this.analysis.medicalCenter == '' || this.analysis.medicalCenter == null){
            Swal.fire('Ingrese el centro médico donde se realizará el estudio', 'El compuesto activo es requisito para cargar el medicamento.', 'warning');
            return;
        }

        // Check if medicalHistoryModel is defined
        if (!this.analysis) {
            Swal.fire('Error', 'No se proporcionó la historia médica.', 'error');
            return;
        }

        // Check if userService is defined
        if (!this.userService) {
            Swal.fire('Error', 'No se proporcionó el servicio de usuario.', 'error');
            return;
        }

        const addAnalysisObservable = this.userService.addAnalysis(this.analysis);

        if (addAnalysisObservable == undefined) {
            Swal.fire('Error', 'El método createMedicalHistory no devuelve un observable.', 'error');
            return;
        }

        addAnalysisObservable.subscribe(
            (data) => {
                Swal.fire('Estudio registrado', 'Estudio registrado con éxito en el sistema.', 'success');
                this.router.navigate(['medic/attendPatient']);
            },
            (error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erróneos.', 'error');
            }
        );
    }
}
