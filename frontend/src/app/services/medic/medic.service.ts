import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class MedicService {

    constructor(private http:HttpClient) { }

    public loginMedic(medic:any){
        return this.http.post(`http://localhost:8080/medic/login`,medic);
    }

    public linkPatient(patient:any){
        return this.http.post(`http://localhost:8080/medic/attendPatient`,patient);
    }

    public getPatientsList() {
        return this.http.get('http://localhost:8080/patient/getAll', {});
    }

    public addMedic(medic:any){
        return this.http.post(`http://localhost:8080/medic/signup`,medic);
    }

    public addMedicine(medicine:any){
        return this.http.post(`http://localhost:8080/medic/addMedicine`,medicine);
    }

    public getMedicinesList() {
        return this.http.get('http://localhost:8080/medicine/findAllMedicine', {});
    }

    public addAnalysis(analysis:any){
        return this.http.post(`http://localhost:8080/medic/addAnalysis`,analysis);
    }

    public createMedicalHistory(medical_history:any){
        return this.http.post(`http://localhost:8080/medic/createMedicalHistory`,medical_history);
    }

    logoutMedic(): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders();

        if (token) {
            headers.set('Authorization', token);
        }

        return this.http.post('http://localhost:8080/medic/logout', {}, { headers });
    }

}
