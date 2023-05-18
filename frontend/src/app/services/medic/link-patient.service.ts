import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class LinkPatientService {

    constructor(private http:HttpClient) { }

    public linkPatient(patient:any){
        return this.http.post(`http://localhost:8080/medic/attendPatient`,patient);
    }

}
