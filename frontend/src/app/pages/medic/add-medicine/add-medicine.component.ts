import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import {MedicService} from "../../../services/medic/medic.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-alta-medicamento',
  templateUrl: './add-medicine.component.html',
  styleUrls: ['./add-medicine.component.css']
})
export class AddMedicineComponent implements OnInit{
    public medicine = {
        medicineName: '',
        activeIngredient: '',
        description: '',
        lab: '',
        status: ''
    }

    constructor(private userService:MedicService, private router:Router){
    }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }


    formSubmit(){
        console.log(this.medicine);
        if(this.medicine.medicineName == '' || this.medicine.medicineName == null){
            Swal.fire('Ingrese el nombre del medicamento', 'El nombre es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medicine.activeIngredient == '' || this.medicine.activeIngredient == null){
            Swal.fire('Ingrese el compuesto activo', 'El compuesto activo es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medicine.lab == '' || this.medicine.lab == null){
            Swal.fire('Ingrese el laboratorio', 'El laboratorio es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medicine.description == '' || this.medicine.description == null){
            Swal.fire('Ingrese la descripción', 'La descripción es requisito para cargar el medicamento.', 'warning');
            return;
        }
        if(this.medicine.status == '' || this.medicine.status == null){
            Swal.fire('Ingrese el estado', 'El estado es requisito para cargar el medicamento.', 'warning');
            return;
        }
        this.userService.addMedicine(this.medicine).subscribe(
            (data) => {
                //this.router.navigate(['medic/home']);
                Swal.fire('Medicamento guardado', 'Medicamento guardado con éxito en el sistema.', 'success');
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
