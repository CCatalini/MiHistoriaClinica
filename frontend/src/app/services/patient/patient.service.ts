import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

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

}
