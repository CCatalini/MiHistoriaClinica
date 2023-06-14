import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    constructor(private http:HttpClient, private router: Router) { }

    public loginPatient(patient: any) {
        return this.http.post(`http://localhost:8080/patient/login`, patient);
    }
    
    public addPatient(patient:any){
        return this.http.post(`http://localhost:8080/patient/signup`,patient);
    }

    public getMedicsList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/patient/get-medics', { headers: headers });
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

        return this.http.post<string>('http://localhost:8080/patient/generate-link-code', null, { headers });
    }

    public getMedicalHistory(token: string): Observable<string> {
        let headers = new HttpHeaders();
        if (token){
            headers = headers.set('Autorization', "Bearer " + token);
        }
        return this.http.get<string>('http://localhost:8080/patient/medical-history');
    }

    public getMedicinesList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/patient/get-medicines', { headers: headers });
    }

    logoutPatient(): Observable<any> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', token);
        }
        return this.http.post('http://localhost:8080/patient/logout', {}, { headers });
    }

    updateMedicineStatus(medicineId: number, status: string) {
        const token = localStorage.getItem('token');
        const url = `http://localhost:8080/patient/update-medicine-status?medicineId=${medicineId}&status=${status}`;

        if (token) {
            return this.http.put(url, null, {
                headers: { Authorization: `Bearer ${token}` }
            });
        } else {
            // Manejar el caso en el que no se encuentre el token en el local storage
            return null; // O devuelve un Observable vacío o null, dependiendo de tu necesidad
        }
    }

}
