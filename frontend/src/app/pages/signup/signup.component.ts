import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{

    public user = {
        name: '',
        surname: '',
        password: '',
        DNI: '',
        date_of_birth: '',
        email: ''
    }

    constructor(private userService:UserService){

    }

    ngOnInit():void{
    }

    formSubmit(){
      console.log(this.user);
      if(this.user.DNI == '' || this.user.DNI == null){
          alert('El nombre de usuario es requerido');
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
