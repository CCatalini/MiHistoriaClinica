import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";

@Component({
    selector: 'app-add-turno',
    templateUrl: './add-turno.component.html',
    styleUrls: ['./add-turno.component.css']
})
export class AddTurnoComponent implements OnInit {

    constructor(private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            window.location.href = '/patient/login';
        }
    }

}
