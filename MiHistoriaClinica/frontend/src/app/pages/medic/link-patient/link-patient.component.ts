import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {MedicService} from "../../../services/medic/medic.service";

@Component({
  selector: 'app-link-patient',
  templateUrl: './link-patient.component.html',
  styleUrls: ['./link-patient.component.css']
})
export class LinkPatientComponent implements OnInit{
    public patient = {
        code: '',
    }

    // Para manejar consultas desde turnos
    turnoId: number | null = null;
    turnoInfo: any = null;
    isFromTurno: boolean = false;

    constructor(
        private userService: MedicService, 
        private router: Router,
        private route: ActivatedRoute
    ){}

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        }

        // Verificar si viene desde un turno
        this.route.queryParams.subscribe(params => {
            if (params['turnoId']) {
                this.turnoId = +params['turnoId'];
                this.isFromTurno = true;
                this.loadTurnoInfo();
            }
        });
    }

    loadTurnoInfo(): void {
        // Cargar información del turno para mostrar al médico
        this.userService.getTodayPendingPatients().subscribe({
            next: (patients) => {
                this.turnoInfo = patients.find(p => p.turnoId === this.turnoId);
            },
            error: (error) => {
                console.log('Error cargando info del turno:', error);
            }
        });
    }

    formatTime(time: string): string {
        if (!time) return '';
        // Formato HH:MM
        return time.substring(0, 5);
    }

    formSubmit(){
        console.log(this.patient);
        if(this.patient.code == '' || this.patient.code == null){
            Swal.fire('Ingrese el código del paciente', 'El código del paciente es requisito para atenderlo.', 'warning');
            return;
        }
        this.userService.linkPatient(this.patient.code).subscribe(
            (data) => {
                console.log(data);
                localStorage.setItem('patientLinkCode', this.patient.code);
                
                // Si viene de un turno, guardar el turnoId para referencia
                if (this.turnoId) {
                    localStorage.setItem('currentTurnoId', this.turnoId.toString());
                }
                
                this.router.navigate(['medic/attendPatient']);
            },(error) => {
                console.log(error);
                Swal.fire('Error', 'El código del paciente es incorrecto.', 'error');
            }
        )
    }
}
