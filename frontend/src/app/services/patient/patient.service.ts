import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    constructor(private http:HttpClient) { }

    public loginPatient(patient: any) {
        return this.http.post(`http://localhost:8080/patient/login`, patient);
    }
    
    public addPatient(patient:any){
        return this.http.post(`http://localhost:8080/patient/signup`,patient);
    }

    public getMedicsList() {
        return this.http.get('http://localhost:8080/medic/getAll');
    }

    public generateLinkCode() {
        return this.http.get('http://localhost:8080/patient/generate-link-code');
    }

    public getMedicalHistory(): Observable<any[]> {
        return this.http.get<any[]>('http://localhost:8080/patient/medical-history/getAll');
    }

}
