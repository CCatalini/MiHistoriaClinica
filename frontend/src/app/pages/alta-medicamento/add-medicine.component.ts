import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import {AddMedicineService} from "../../services/medicine/add-medicine.service";

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
        this.userService.addMedicine(this.medic).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Usuario guardado', 'Usuario registrado con éxito en el sistema.', 'success');
            }
        )
    }
}
