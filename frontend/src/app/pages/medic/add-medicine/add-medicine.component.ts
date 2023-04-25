import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import {AddMedicineService} from "../../../services/medicine/add-medicine.service";

@Component({
  selector: 'app-alta-medicamento',
  templateUrl: './add-medicine.component.html',
  styleUrls: ['./add-medicine.component.css']
})
export class AddMedicineComponent implements OnInit{
    public medic = {
        medicineName: '',
        activeIngredient: '',
        description: '',
        lab: ''
    }

    constructor(private userService:AddMedicineService, private snack:MatSnackBar){
    }

    ngOnInit(): void {
    }

    formSubmit(){
        console.log(this.medic);
        if(this.medic.medicineName == '' || this.medic.medicineName == null){
            Swal.fire('Ingrese el nombre del medicamento', 'El nombre es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medic.activeIngredient == '' || this.medic.activeIngredient == null){
            Swal.fire('Ingrese el compuesto activo', 'El compuesto activo es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medic.lab == '' || this.medic.lab == null){
            Swal.fire('Ingrese el laboratorio', 'El laboratorio es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medic.description == '' || this.medic.description == null){
            Swal.fire('Ingrese la descripción', 'La descripción es requisito para cargar el medicamento.', 'warning');
            return;
        }
        this.userService.addMedicine(this.medic).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Usuario guardado', 'Usuario registrado con éxito en el sistema.', 'success');
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
