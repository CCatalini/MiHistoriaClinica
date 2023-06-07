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

    public getMedicsList() {
        return this.http.get('http://localhost:8080/medic/getAll');
    }

    /**
     * De esta manera el metodo espera recibir un string que manda el back en vez de un objeto JSON
     */
    public generateLinkCode(): Observable<string> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', token);
        }

        return this.http.post<string>('http://localhost:8080/patient/generate-link-code', null, { headers });
    }

    public getMedicalHistory(token: string): Observable<string> {
        let headers = new HttpHeaders();
        if (token){
            headers = headers.set('Autorization', "Bearer" + token);
        }
        return this.http.get<string>('http://localhost:8080/patient/medical-history');
    }

    public logoutPatient(): Observable<any> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', token);
        }
        return this.http.post<string>('http://localhost:8080/patient/logout', {}, { headers });
    }


}
