import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, of} from "rxjs";
import {Router} from "@angular/router";

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
        return this.http.post(`http://localhost:8080/patient/signup`, patient);
    }

    public getMedicsList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/patient/get-medics', {headers: headers});
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

    /*public getMedicalHistory(): Observable<string> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        return this.http.get<string>('http://localhost:8080/patient/get-medical-history', {headers: headers});
    }*/

    public getMedicalHistory(): Observable<string> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
        }
        return this.http.get<string>('http://localhost:8080/patient/get-medical-history', {headers: headers});
    }

    public getMedicinesList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/patient/get-medicines', {headers: headers});
    }

    public getAppointmentsList(token: string): Observable<any[]>{
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/medicalAppointment/patient/get', {headers: headers});
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

        return this.http.put("http://localhost:8080/patient/update-medicine-status", null, {params: params});
    }

}
