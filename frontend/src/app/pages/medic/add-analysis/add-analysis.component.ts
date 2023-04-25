import { Component } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import {AddAnalysisService} from "../../../services/analysis/analysis.service";

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

    constructor(private userService:AddAnalysisService, private snack:MatSnackBar){
    }

    ngOnInit(): void {
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

        this.userService.addAnalysis(this.analysis).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Analisis guardado', 'El análisis médico fue cargado con éxito en el sistema.', 'success');
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
