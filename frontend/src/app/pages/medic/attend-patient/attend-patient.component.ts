
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-attend-patient',
  templateUrl: './attend-patient.component.html',
  styleUrls: ['./attend-patient.component.css']
})
export class AttendPatientComponent implements OnInit {
    patientId : string = '';
    constructor(private route:ActivatedRoute) { }


    //TODO EL CODIGO HACE LO QUE SE LE CANTA EL ORTO, HAY QUE VER QUE EL QUE SE GENERA POR ACA Y MUESTRA EL FRONT SEA EL MISMO QUE GUARDA LA TABLA JUJU
    ngOnInit(): void {
        //verifico usuario
        /*if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        } else {
            this.route.params.subscribe(params => {
                this.patientId = params['linkCode'];
                console.log(this.patientId);
                // Use the value of this.id as needed
            });
        }*/
    }
}
