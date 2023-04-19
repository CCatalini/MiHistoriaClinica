import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";

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

    constructor(private userService:UserService){

    }

    ngOnInit():void{
        console.log("Hola");
    }

    formSubmit(){
      console.log(this.user);
      if(this.user.dni == '' || this.user.dni == null){
          alert('El DNI es requerido');
          return;
      }

      this.userService.añadirUsuario(this.user).subscribe(
          (data) => {
              console.log(data);
              alert('Usuario guardado con exito');
          },(error) => {
              console.log(error);
              alert('Ha ocurrido un eror en el sistema');
          }
      )


    }
}
