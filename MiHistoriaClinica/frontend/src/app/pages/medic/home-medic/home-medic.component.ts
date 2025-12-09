import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MedicService} from '../../../services/medic/medic.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home-medic',
  templateUrl: './home-medic.component.html',
  styleUrls: ['./home-medic.component.css']
})
export class HomeMedicComponent implements OnInit{
    todayPatients: any[] = [];
    isLoading: boolean = true;
    
    // Modal
    showModal: boolean = false;
    selectedPatient: any = null;
    codigoVinculacion: string = '';

    constructor(private medicService: MedicService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'MEDIC') {
            window.location.href = '/medic/login';
        } else {
            this.loadTodayPatients();
        }
    }

    loadTodayPatients(): void {
        this.isLoading = true;
        this.medicService.getTodayAllPatients().subscribe({
            next: (data) => {
                this.todayPatients = data || [];
                // Verificar turnos vencidos
                this.checkExpiredTurnos();
                this.isLoading = false;
            },
            error: (error) => {
                console.log('Error cargando pacientes del día:', error);
                this.todayPatients = [];
                this.isLoading = false;
            }
        });
    }

    /**
     * Verifica si hay turnos vencidos (más de 1 hora desde la hora asignada)
     * y actualiza su estado a VENCIDO
     */
    checkExpiredTurnos(): void {
        const now = new Date();
        const currentHours = now.getHours();
        const currentMinutes = now.getMinutes();
        
        this.todayPatients.forEach(patient => {
            if (patient.estadoConsulta?.toLowerCase() === 'pendiente' && patient.time) {
                const [hours, minutes] = patient.time.split(':').map(Number);
                const turnoTime = hours * 60 + minutes;
                const currentTime = currentHours * 60 + currentMinutes;
                
                // Si pasó más de 1 hora (60 minutos)
                if (currentTime - turnoTime > 60) {
                    patient.estadoConsulta = 'Vencido';
                    // Actualizar en el backend
                    this.medicService.updateTurnoEstado(patient.turnoId, 'Vencido').subscribe({
                        error: (err) => console.log('Error actualizando estado vencido:', err)
                    });
                }
            }
        });
    }

    formatTime(time: string): string {
        if (!time) return '';
        // Formato HH:MM
        return time.substring(0, 5);
    }

    // Modal functions
    abrirModal(patient: any): void {
        this.selectedPatient = patient;
        this.codigoVinculacion = '';
        this.showModal = true;
    }

    cerrarModal(): void {
        this.showModal = false;
        this.selectedPatient = null;
        this.codigoVinculacion = '';
    }

    confirmarCodigo(): void {
        if (!this.codigoVinculacion || this.codigoVinculacion.trim() === '') {
            Swal.fire('Error', 'Ingrese el código del paciente', 'warning');
            return;
        }

        this.medicService.linkPatient(this.codigoVinculacion).subscribe({
            next: (data) => {
                localStorage.setItem('patientLinkCode', this.codigoVinculacion);
                if (this.selectedPatient?.turnoId) {
                    localStorage.setItem('currentTurnoId', this.selectedPatient.turnoId.toString());
                }
                this.cerrarModal();
                this.router.navigate(['medic/attendPatient']);
            },
            error: (error) => {
                console.log(error);
                Swal.fire('Error', 'El código del paciente es incorrecto.', 'error');
            }
        });
    }

    /**
     * Cancela un turno con confirmación
     */
    cancelarConsulta(patient: any): void {
        Swal.fire({
            title: '¿Cancelar consulta?',
            text: `¿Estás seguro de cancelar la consulta de ${patient.patientFullName}?`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, cancelar',
            cancelButtonText: 'No'
        }).then((result) => {
            if (result.isConfirmed) {
                this.medicService.cancelarTurno(patient.turnoId).subscribe({
                    next: () => {
                        patient.estadoConsulta = 'Cancelada';
                        Swal.fire({
                            title: 'Consulta cancelada',
                            text: 'La consulta ha sido cancelada correctamente.',
                            icon: 'success',
                            confirmButtonColor: '#3fb5eb'
                        });
                    },
                    error: (error) => {
                        console.log('Error cancelando consulta:', error);
                        Swal.fire('Error', 'No se pudo cancelar la consulta.', 'error');
                    }
                });
            }
        });
    }

    getEstadoClass(estado: string): string {
        switch (estado?.toLowerCase()) {
            case 'pendiente': return 'estado-pendiente';
            case 'realizada': return 'estado-realizada';
            case 'completada': return 'estado-realizada'; // Por compatibilidad
            case 'cancelada': return 'estado-cancelada';
            case 'vencido': return 'estado-vencido';
            default: return 'estado-pendiente';
        }
    }

    isPendiente(estado: string): boolean {
        return estado?.toLowerCase() === 'pendiente';
    }

    /**
     * Verifica si un turno puede ser cancelado (solo si está pendiente)
     */
    puedeCancelar(estado: string): boolean {
        return estado?.toLowerCase() === 'pendiente';
    }
}
