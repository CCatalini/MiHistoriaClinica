import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";
import { Router } from '@angular/router';
import {MedicService} from "../../../services/medic/medic.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-signup-medic',
  templateUrl: './signup-medic.component.html',
  styleUrls: ['./signup-medic.component.css']
})

export class SignupMedicComponent implements OnInit{

    public medic = {
        name: '',
        lastname: '',
        email: '',
        dni: '',
        matricula: '',
        specialty: '',
        password: ''
    }

    specialtyOptions: string[] = [];

    constructor(private userService:MedicService,
                private snack:MatSnackBar,
                private router: Router){}

    ngOnInit():void{

        this.getSpecialtyOptions().subscribe((options) => {
            this.specialtyOptions = options;
        });
    }

    formSubmit(){
        console.log(this.medic);
        if(this.medic.dni == '' || this.medic.dni == null){
            Swal.fire('Ingrese su DNI', 'El DNI es requisito para registrarse como paciente.', 'warning');
            return;
        }

        this.userService.addMedic(this.medic).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Médico registrado', 'Usuario registrado con éxito en el sistema.', 'success');
                this.router.navigate(['medic/login']);
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }


    getSpecialtyOptions(): Observable<string[]> {
        return this.userService.getSpecialtyOptions();
    }
}
