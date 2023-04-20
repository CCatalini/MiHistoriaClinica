import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import Swal from "sweetalert2";

@Component({
  selector: 'app-signup-medic',
  templateUrl: './signup-medic.component.html',
  styleUrls: ['./signup-medic.component.css']
})

export class SignupMedicComponent implements OnInit{

    public user = {
        name: '',
        lastname: '',
        email: '',
        dni: '',
        matricula: '',
        specialty: '',
        password: ''
    }

    constructor(private userService:UserService, private snack:MatSnackBar){

    }

    ngOnInit():void{
        console.log("Hola");
    }

    formSubmit(){
        console.log(this.user);
        if(this.user.dni == '' || this.user.dni == null){
            Swal.fire('Ingrese su DNI', 'El DNI es requisito para registrarse como paciente.', 'warning');
            return;
        }

        this.userService.añadirUsuario(this.user).subscribe(
            (data) => {
                console.log(data);
                Swal.fire('Usuario guardado', 'Usuario registrado con éxito en el sistema.', 'success');
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Falta completar algún campo o existen datos repetidos.', 'error');
            }
        )


    }
}
