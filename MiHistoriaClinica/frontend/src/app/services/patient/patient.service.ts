import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {map, Observable, of} from "rxjs";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    constructor(private http: HttpClient, private router: Router) {
    }

    public loginPatient(patient: any) {
        return this.http.post(`http://localhost:8080/patient/login`, patient);
    }

    public addPatient(patient: any) {
        return this.http.post(`http://localhost:8080/auth/patient/register`, patient);
    }

    

    public getMedicsList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/patient/get-medics', {headers: headers});
    }

    public getTurnosList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/turno/patient/get-turnos', {headers: headers});
    }

    public deleteTurno(turnoId: number) {
        const params = new HttpParams().set('turnoId', turnoId.toString());

        return this.http.delete('http://localhost:8080/turno/patient/delete-turno', {params: params})
            .pipe(
                map(() => {
                    Swal.fire(
                        'Eliminado',
                        'El turno ha sido eliminado correctamente.',
                        'success'
                    );
                })
            );
    }

    public getAllMedicsList() {
        return this.http.get<any[]>('http://localhost:8080/medic/get-all');
    }

    /**
     * De esta manera el metodo espera recibir un string que manda el back en vez de un objeto JSON
     */
    public generateLinkCode(): Observable<string> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        return this.http.post<string>('http://localhost:8080/patient/generate-link-code', null, {headers});
    }

    public getMedicalFile(): Observable<string> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        return this.http.get<string>('http://localhost:8080/medical-file/get-medical-file', {headers: headers});
    }

    public getMedicinesList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/medicine/get-medicines', {headers: headers});
    }

    public getAnalysisList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/analysis/patient/get-analysis', {headers: headers});
    }

    public getAppointmentsList(token: string): Observable<any[]>{
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/medicalAppointment/patient/get', {headers: headers});
    }

    getMedicinesByStatus(status: string) {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        let params = new HttpParams().set("status", status);
        return this.http.get<any[]>("http://localhost:8080/medicine/getMedicinesByStatus", { headers: headers, params: params });
    }

    getAnalysisByStatus(status: string) {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        let params = new HttpParams().set("status", status);
        return this.http.get<any[]>("http://localhost:8080/analysis/patient/get-analysis-byStatus", { headers: headers, params: params });
    }

    getMedicsBySpecialty(specialty: string) {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        let params = new HttpParams().set("specialty", specialty);
        return this.http.get<any[]>("http://localhost:8080/patient/get-medics-by-specialty", { headers: headers, params: params });
    }

    public getAllSpecialties() {
        return this.http.get<string[]>('http://localhost:8080/medic/all-specialties');
    }

    logoutPatient(): Observable<any> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', token);
        }
        return this.http.post('http://localhost:8080/patient/logout', {}, {headers});
    }

    updateMedicineStatus(medicineId: number, status: string) {
        let params = new HttpParams()
            .set("medicineId", medicineId.toString())
            .set("status", status);

        return this.http.put("http://localhost:8080/medicine/update-medicine-status", null, {params: params});
    }

    updateAnalysisStatus(analysis_id: number, status: string) {
        let params = new HttpParams().set("status", status);
        let headers = new HttpHeaders().set("analysis_id", analysis_id.toString())

        return this.http.put("http://localhost:8080/analysis/patient/update-analysis-status", null, {headers: headers, params: params});
    }

    addTurno(turnoDTO: any, medicId: string): Observable<any> {
        let headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        const token = localStorage.getItem('token');
        headers = headers.set('Authorization', "Bearer " + token);
        const body = {
            fechaTurno: turnoDTO.fechaTurno,
            horaTurno: turnoDTO.horaTurno,
        };
        const params = {
            medicId: medicId,
            medicalCenter: turnoDTO.medicalCenter
        };
        return this.http.post('http://localhost:8080/turno/patient/create-turno', body, { headers: headers, params: params });
    }

}


