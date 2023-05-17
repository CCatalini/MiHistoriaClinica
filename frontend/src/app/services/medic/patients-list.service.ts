import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class PatientsListService {

    constructor(private http: HttpClient) { }

    public getPatientsList() {
        return this.http.get('http://localhost:8080/patient/getAll', {});
    }

}
