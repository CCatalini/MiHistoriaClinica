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


/*
    public generateLinkCode(token:any) {
        return this.http.post('http://localhost:8080/patient/generate-link-code', token);
    }
*/

    /**
     * De esta manera el metodo espera recibir un string que manda el back en vez de un objeto JSON
     */
    public generateLinkCode(): Observable<string> {
        const token = localStorage.getItem('token');

        // Configurar el encabezado de autorización
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });

        return this.http.post<string>('http://localhost:8080/patient/generate-link-code', null, { headers });
    }

    public getMedicalHistory(): Observable<any[]> {
        return this.http.get<any[]>('http://localhost:8080/patient/medical-history/getAll');
    }

    logoutPatient(): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders().set('Authorization', token);

        return this.http.post('/logout', {}, { headers });
    }

}
