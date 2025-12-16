import {Component, OnInit} from '@angular/core';
import {PatientService} from "../../../services/patient/patient.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: 'app-turnos-list',
  templateUrl: './turnos-list.component.html',
  styleUrls: ['./turnos-list.component.css']
})
export class TurnosListComponent implements OnInit{
    turnos: any[] = [];
    turnosOriginales: any[] = [];
    turnosFiltrados: any[] = [];

    // Filtros
    filtroEstado: string = '';
    filtroEspecialidad: string = '';
    filtroMedico: string = '';
    filtroFechaDesde: string = '';
    filtroFechaHasta: string = '';
    ordenarPor: string = 'fecha-asc';

    // Opciones de filtros
    especialidadesDisponibles: string[] = [];
    medicosDisponibles: string[] = [];

    constructor(private userService: PatientService, private router: Router) { }

    ngOnInit(): void {
        //verifico usuario
        if (localStorage.getItem('userType') != 'PATIENT') {
            this.router.navigate(['/patient/login']);
        } else {
            this.formSubmit(); // Creamos medics list
        }
    }

    formSubmit() {
        const token = localStorage.getItem('token');
        if(token) {
            this.userService.getTurnosList(token).subscribe(
                (data: any) => {
                    console.log('Turnos:', data);
                    this.turnos = Array.isArray(data) ? data : [];
                    this.turnosOriginales = [...this.turnos];
                    this.turnosFiltrados = [...this.turnos];
                    this.extraerOpcionesFiltros();
                    this.aplicarFiltros();
                },
                (error: any) => {
                    console.log('Error fetching turnos:', error);
                    // Solo mostrar error si es un error real del servidor (500), no si simplemente no hay datos
                    if (error.status >= 500) {
                        Swal.fire('Error', 'Se produjo un error en el servidor.', 'error');
                    }
                    this.turnos = [];
                    this.turnosOriginales = [];
                    this.turnosFiltrados = [];
                }
            );
        } else {
            // Manejar el caso en el que no se encuentre el token en el local storage
        }
    }

    /**
     * Extrae las opciones únicas de especialidades y médicos para los filtros
     */
    extraerOpcionesFiltros(): void {
        // Extraer especialidades únicas
        const especialidades = new Set<string>();
        this.turnosOriginales.forEach(turno => {
            const esp = this.formatSpecialty(turno.medicSpecialty);
            if (esp) especialidades.add(esp);
        });
        this.especialidadesDisponibles = Array.from(especialidades).sort();

        // Extraer médicos únicos
        const medicos = new Set<string>();
        this.turnosOriginales.forEach(turno => {
            if (turno.medicFullName) medicos.add(turno.medicFullName);
        });
        this.medicosDisponibles = Array.from(medicos).sort();
    }

    /**
     * Aplica todos los filtros y ordenamiento
     */
    aplicarFiltros(): void {
        let resultado = [...this.turnosOriginales];

        // Filtro por estado
        if (this.filtroEstado) {
            resultado = resultado.filter(turno => {
                const estado = this.getEstadoTexto(turno).toLowerCase();
                return estado === this.filtroEstado.toLowerCase();
            });
        }

        // Filtro por especialidad
        if (this.filtroEspecialidad) {
            resultado = resultado.filter(turno => 
                this.formatSpecialty(turno.medicSpecialty) === this.filtroEspecialidad
            );
        }

        // Filtro por médico
        if (this.filtroMedico) {
            resultado = resultado.filter(turno => 
                turno.medicFullName === this.filtroMedico
            );
        }

        // Filtro por fecha desde
        if (this.filtroFechaDesde) {
            resultado = resultado.filter(turno => 
                turno.fechaTurno >= this.filtroFechaDesde
            );
        }

        // Filtro por fecha hasta
        if (this.filtroFechaHasta) {
            resultado = resultado.filter(turno => 
                turno.fechaTurno <= this.filtroFechaHasta
            );
        }

        // Ordenamiento
        resultado = this.ordenarTurnos(resultado);

        this.turnosFiltrados = resultado;
    }

    /**
     * Ordena los turnos según el criterio seleccionado
     */
    ordenarTurnos(turnos: any[]): any[] {
        switch (this.ordenarPor) {
            case 'fecha-asc':
                return turnos.sort((a, b) => {
                    const dateA = new Date(a.fechaTurno + 'T' + a.horaTurno);
                    const dateB = new Date(b.fechaTurno + 'T' + b.horaTurno);
                    return dateA.getTime() - dateB.getTime();
                });
            case 'fecha-desc':
                return turnos.sort((a, b) => {
                    const dateA = new Date(a.fechaTurno + 'T' + a.horaTurno);
                    const dateB = new Date(b.fechaTurno + 'T' + b.horaTurno);
                    return dateB.getTime() - dateA.getTime();
                });
            case 'medico':
                return turnos.sort((a, b) => 
                    (a.medicFullName || '').localeCompare(b.medicFullName || '')
                );
            case 'especialidad':
                return turnos.sort((a, b) => 
                    this.formatSpecialty(a.medicSpecialty).localeCompare(this.formatSpecialty(b.medicSpecialty))
                );
            default:
                return turnos;
        }
    }

    /**
     * Limpia todos los filtros
     */
    limpiarFiltros(): void {
        this.filtroEstado = '';
        this.filtroEspecialidad = '';
        this.filtroMedico = '';
        this.filtroFechaDesde = '';
        this.filtroFechaHasta = '';
        this.ordenarPor = 'fecha-asc';
        this.aplicarFiltros();
    }

    formatDate(dateStr: string): string {
        if (!dateStr) return '';
        const date = new Date(dateStr + 'T12:00:00');
        const days = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];
        const months = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
        return `${days[date.getDay()]} ${date.getDate()} ${months[date.getMonth()]}`;
    }

    formatMedicalCenter(center: string): string {
        if (!center) return '';
        // Convertir SEDE_PRINCIPAL_HOSPITAL_AUSTRAL a "Sede Principal - Hospital Austral"
        return center
            .split('_')
            .map((word, index, arr) => {
                // Capitalizar primera letra, resto en minúscula
                const formatted = word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
                // Agregar guión antes de "Hospital" o "Clinica", pero NO si es la primera palabra
                if (index > 0 && (word.toLowerCase() === 'hospital' || word.toLowerCase() === 'clinica')) {
                    return '- ' + formatted;
                }
                return formatted;
            })
            .join(' ')
            .replace('  ', ' ');
    }

    formatSpecialty(specialty: any): string {
        if (specialty === null || specialty === undefined) return '';
        
        // Mapeo de ordinales a nombres legibles (según el orden del enum en Java)
        const ordinalMap: {[key: number]: string} = {
            0: 'Cardiología',
            1: 'Dermatología',
            2: 'Endocrinología',
            3: 'Gastroenterología',
            4: 'Hematología',
            5: 'Infectología',
            6: 'Neurología',
            7: 'Oncología',
            8: 'Oftalmología',
            9: 'Otorrinolaringología',
            10: 'Pediatría',
            11: 'Psiquiatría',
            12: 'Radiología',
            13: 'Reumatología',
            14: 'Traumatología',
            15: 'Urología',
            16: 'Ginecología',
            17: 'Medicina Interna',
            18: 'Cirugía General',
            19: 'Anestesiología',
            20: 'Medicina Clínica'
        };
        
        // Si es un número (ordinal), usar el mapeo
        if (typeof specialty === 'number') {
            return ordinalMap[specialty] || `Especialidad ${specialty}`;
        }
        
        // Si es string con formato ENUM (CARDIOLOGIA, MEDICINA_CLINICA, etc.)
        if (typeof specialty === 'string') {
            const stringMap: {[key: string]: string} = {
                'CARDIOLOGIA': 'Cardiología',
                'DERMATOLOGIA': 'Dermatología',
                'ENDOCRINOLOGIA': 'Endocrinología',
                'GASTROENTEROLOGIA': 'Gastroenterología',
                'HEMATOLOGIA': 'Hematología',
                'INFECTOLOGIA': 'Infectología',
                'NEUROLOGIA': 'Neurología',
                'ONCOLOGIA': 'Oncología',
                'OFTALMOLOGIA': 'Oftalmología',
                'OTORRINOLARINGOLOGIA': 'Otorrinolaringología',
                'PEDIATRIA': 'Pediatría',
                'PSIQUIATRIA': 'Psiquiatría',
                'RADIOLOGIA': 'Radiología',
                'REUMATOLOGIA': 'Reumatología',
                'TRAUMATOLOGIA': 'Traumatología',
                'UROLOGIA': 'Urología',
                'GINECOLOGIA': 'Ginecología',
                'MEDICINA_INTERNA': 'Medicina Interna',
                'CIRUGIA_GENERAL': 'Cirugía General',
                'ANESTESIOLOGIA': 'Anestesiología',
                'MEDICINA_CLINICA': 'Medicina Clínica'
            };
            return stringMap[specialty] || specialty.replace(/_/g, ' ');
        }
        
        return String(specialty);
    }

    formatTime(time: string): string {
        if (!time) return '';
        // Formato HH:MM (sin segundos)
        return time.substring(0, 5);
    }

    /**
     * Obtiene el estado del turno considerando si ya pasó la hora
     */
    getEstadoTexto(turno: any): string {
        // Si tiene estado del backend, usarlo
        if (turno.estadoConsulta) {
            const estado = turno.estadoConsulta.toLowerCase();
            if (estado === 'realizada') return 'Realizada';
            if (estado === 'cancelada') return 'Cancelada';
            if (estado === 'vencido') return 'Vencido';
        }
        
        // Si está pendiente, verificar si ya pasó la hora
        const now = new Date();
        const turnoDate = new Date(turno.fechaTurno + 'T' + turno.horaTurno);
        
        if (turnoDate < now) {
            return 'Vencido';
        }
        
        return 'Pendiente';
    }

    /**
     * Obtiene la clase CSS para el badge de estado
     */
    getEstadoClass(turno: any): string {
        const estado = this.getEstadoTexto(turno).toLowerCase();
        switch (estado) {
            case 'pendiente': return 'estado-pendiente';
            case 'realizada': return 'estado-realizada';
            case 'cancelada': return 'estado-cancelada';
            case 'vencido': return 'estado-vencido';
            default: return 'estado-pendiente';
        }
    }

    /**
     * Obtiene la clase CSS para la fila según el estado
     */
    getRowClass(turno: any): string {
        const estado = this.getEstadoTexto(turno).toLowerCase();
        if (estado === 'cancelada' || estado === 'vencido') {
            return 'row-inactive';
        }
        if (estado === 'realizada') {
            return 'row-completed';
        }
        return '';
    }

    /**
     * Determina si el turno puede ser cancelado
     * Solo se puede cancelar si está pendiente y no ha pasado la hora
     */
    puedeCancelar(turno: any): boolean {
        const estado = this.getEstadoTexto(turno).toLowerCase();
        return estado === 'pendiente';
    }

    deleteTurno(turno: any) {
        console.log(turno.turnoId);
        if (!turno.turnoId) {
            Swal.fire({
                title: 'Error',
                text: 'ID del turno no válido.',
                icon: 'error',
                confirmButtonColor: '#3fb5eb'
            });
            return;
        }
        Swal.fire({
            title: 'Cancelar Turno Médico',
            text: '¿Estás seguro de que quieres cancelar este turno médico?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3fb5eb',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, cancelar turno',
            cancelButtonText: 'No, mantener turno'
        }).then((result) => {
            if (result.isConfirmed) {
                this.userService.deleteTurno(turno.turnoId).subscribe(
                    () => {
                        Swal.fire({
                            title: '¡Turno Cancelado!',
                            text: 'Tu turno médico ha sido cancelado exitosamente.',
                            icon: 'success',
                            confirmButtonColor: '#3fb5eb'
                        }).then(() => {
                            // Recargar la lista de turnos
                            this.formSubmit();
                        });
                    },
                    (error) => {
                        console.error('Error al cancelar turno:', error);
                        Swal.fire({
                            title: 'Error',
                            text: 'No se pudo cancelar el turno. Por favor, intenta nuevamente.',
                            icon: 'error',
                            confirmButtonColor: '#3fb5eb'
                        });
                    }
                );
            }
        });
    }
}
