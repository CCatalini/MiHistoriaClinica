import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import Swal from 'sweetalert2';
@Component({
  selector: 'app-signup-patient',
  templateUrl: './signup-patient.component.html',
  styleUrls: ['./signup-patient.component.css']
})
export class SignupPatientComponent implements OnInit{

    public user = {
        name: '',
        lastname: '',
        dni: '',
        email: '',
        password: '',
        birthday: ''
    }

    constructor(private userService:UserService, private snack:MatSnackBar){

    }

    ngOnInit():void{
        console.log("Hola");
    }

    formSubmit(){
      console.log(this.user);
      if(this.user.dni == '' || this.user.dni == null){
          this.snack.open("El DNI es requerido", "Aceptar",{
              duration: 3000,
              verticalPosition: 'top',
              horizontalPosition: 'right'
          });
          return;
      }

      this.userService.añadirUsuario(this.user).subscribe(
          (data) => {
              console.log(data);
              Swal.fire('Usuario guardado', 'Usuario registrado con éxito en el sistema', 'success');
          },(error) => {
              console.log(error);
              this.snack.open("Ha ocurrido un error en el sistema", "Aceptar",{
                  duration: 3000,
                  verticalPosition: 'top',
                  horizontalPosition: 'right'
              });          }
      )


    }
}
