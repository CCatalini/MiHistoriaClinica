import {Component, OnInit} from '@angular/core';
import {PatientService} from "../../../services/patient/patient.service";
import {Router} from "@angular/router";
import {MedicService} from "../../../services/medic/medic.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-appointments-list-medic',
  templateUrl: './appointments-list-medic.component.html',
  styleUrls: ['./appointments-list-medic.component.css']
})
export class AppointmentsListMedicComponent implements OnInit{

    appointments: any[] = [];

    constructor(private userService: MedicService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Creamos medics list
        }
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if(token) {
            this.userService.getAppointmentsList().subscribe(
                (data: any) => {
                    console.log(data); // Agregar este console.log para verificar la respuesta del servidor
                    if (Array.isArray(data)) {
                        this.appointments = data;
                    } else {
                        Swal.fire('Error', 'La respuesta del servidor no contiene una lista de medicamentos válida.', 'error');
                    }
                },
                (error: any) => {
                    console.log(error);
                    if (error.status === 400) {
                        Swal.fire('Error', 'Existen datos erróneos.', 'error');
                    } else if (error.status === 404) {
                        Swal.fire('Error', 'No se encontraron pacientes.', 'error');
                    } else {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                }
            );
        } else {
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }
}
