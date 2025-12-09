import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { PatientService } from "../../services/patient/patient.service";
import { MedicService } from "../../services/medic/medic.service";
import { filter } from 'rxjs/operators';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    patient: any = null;
    medic: any = null;

    constructor(
        private router: Router, 
        private route: ActivatedRoute, 
        private patientService: PatientService, 
        private medicService: MedicService
    ) {}

    ngOnInit(): void {
        // Cargar datos iniciales
        this.loadUserData();
        
        // Recargar datos cuando cambie la ruta
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
        ).subscribe(() => {
            this.loadUserData();
        });
    }

    loadUserData(): void {
        const userType = localStorage.getItem('userType');
        const token = localStorage.getItem('token');
        
        if (userType === 'PATIENT' && token && this.isPatientPage()) {
            if (!this.patient) {
                this.patientService.getPatientInfo().subscribe(
                    (data: any) => {
                        this.patient = data;
                    },
                    (error: any) => {
                        console.error('Error al cargar datos del paciente:', error);
                    }
                );
            }
        } else if (userType === 'MEDIC' && token && this.isMedicPage()) {
            if (!this.medic) {
                this.medicService.getMedicInfo().subscribe(
                    (data: any) => {
                        this.medic = data;
                    },
                    (error: any) => {
                        console.error('Error al cargar datos del médico:', error);
                    }
                );
            }
        }
    }

    logoutMedic(): void {
        this.medicService.logoutMedic().subscribe(
            () => {
                localStorage.setItem('token', '');
                localStorage.setItem('userType', '');
                localStorage.setItem('patientLinkCode', '');
                this.medic = null;
                this.router.navigate(['/']);
            },
            (error) => {
                console.error('Error al cerrar sesión:', error);
            }
        );
    }

    logoutPatient(): void {
        this.patientService.logoutPatient().subscribe(
            () => {
                localStorage.setItem('token', '');
                localStorage.setItem('userType', '');
                localStorage.setItem('patientLinkCode', '');
                this.patient = null;
                this.router.navigate(['/']);
            },
            (error) => {
                console.error('Error al cerrar sesión:', error);
            }
        );
    }

    goBack(): void {
        if (this.isMedicalHistoryListPage()) {
            this.router.navigate(['/medic/attendPatient']);
        } else {
            window.history.back();
        }
    }

    isPatientPage(): boolean {
        return this.router.url.includes('/patient') && !this.router.url.includes('/login') && !this.router.url.includes('/signup');
    }

    isMedicPage(): boolean {
        return this.router.url.includes('/medic') && !this.router.url.includes('/login') && !this.router.url.includes('/signup') && !this.router.url.includes('/patient');
    }

    isHomePage() {
        return this.router.url == '/patient/home' || this.router.url == '/medic/home';
    }

    isPrincipalPage() {
        return this.router.url == '/';
    }

    isAttendPatientPage() {
        return this.router.url == '/medic/attendPatient';
    }

    isMedicalHistoryListPage() {
        return this.router.url == '/medic/medicalHistoryList';
    }

    formatSpecialty(specialty: string): string {
        if (!specialty) return '';
        
        const specialtyMap: {[key: string]: string} = {
            'MEDICINA_CLINICA': 'Medicina Clínica',
            'CARDIOLOGIA': 'Cardiología',
            'DERMATOLOGIA': 'Dermatología',
            'ENDOCRINOLOGIA': 'Endocrinología',
            'GASTROENTEROLOGIA': 'Gastroenterología',
            'GINECOLOGIA': 'Ginecología',
            'HEMATOLOGIA': 'Hematología',
            'INFECTOLOGIA': 'Infectología',
            'NEFROLOGIA': 'Nefrología',
            'NEUMOLOGIA': 'Neumología',
            'NEUROLOGIA': 'Neurología',
            'NUTRICION': 'Nutrición',
            'OFTALMOLOGIA': 'Oftalmología',
            'ONCOLOGIA': 'Oncología',
            'OTORRINOLARINGOLOGIA': 'Otorrinolaringología',
            'PEDIATRIA': 'Pediatría',
            'PSIQUIATRIA': 'Psiquiatría',
            'REUMATOLOGIA': 'Reumatología',
            'TRAUMATOLOGIA': 'Traumatología',
            'UROLOGIA': 'Urología'
        };
        
        return specialtyMap[specialty] || specialty.replace(/_/g, ' ');
    }
}
