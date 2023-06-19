import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-home-medic',
  templateUrl: './home-medic.component.html',
  styleUrls: ['./home-medic.component.css']
})
export class HomeMedicComponent implements OnInit{
    constructor() { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }
    }

    // todo agregar metodos que traigan datos del medico para mostrarlos en el encabezado de la pagin
}
