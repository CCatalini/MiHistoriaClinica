import {Component, OnInit} from '@angular/core';
import Swal from "sweetalert2";
import {LoginMedicService} from "../../../services/medic/login-medic.service";
import {Router} from "@angular/router";
import {PatientsListService} from "../../../services/medic/patients-list.service";

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.css']
})
export class PatientsListComponent implements OnInit{

    public patient = {
        name: '',
        lastname: '',
        dni: '',
        email: '',
        birthday: ''
    }

    constructor(private userService:PatientsListService, private router: Router){
    }

    ngOnInit(): void {
    }

    formSubmit(){
        this.userService.getPatientsList(this.patient).subscribe(
            (data) => {
                console.log(data);
                this.router.navigate(['home']);
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'Existen datos erroneos.', 'error');
            }
        )
    }
}
