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

    public linkPatient(linkCode: any) {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization',"Bearer " + token);
        }
        return this.http.post(`http://localhost:8080/medic/linkPatient?linkCode=${linkCode}`, null, { headers });
    }

    public addMedicine(medicine: any, patientLinkCode: string) {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
            headers = headers.set('patientLinkCode', patientLinkCode);
        }
        const body = {
            medicine: medicine,
        };
        return this.http.post('http://localhost:8080/medic/create-medicine', body, { headers });
    }

    public getPatientsList() {
        return this.http.get('http://localhost:8080/medic/get-patients');
    }

    public addMedic(medic:any){
        return this.http.post(`http://localhost:8080/medic/signup`,medic);
    }

    // todo cambiar por el metodo medic/get-medicines cuando esté listo y pasar patientLinkCode del paciente que se está atendiendo
    public getMedicinesList(token: string) {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get('http://localhost:8080/patient/get-medicines', { headers });
    }


    public getAnalysisList() {
        return this.http.get('http://localhost:8080/analysis/findAllAnalysis', {});
    }

    public addAnalysis(analysis:any){
        return this.http.post(`http://localhost:8080/medic/addAnalysis`,analysis);
    }

    public createMedicalHistory(medical_history:any){
        return this.http.post(`http://localhost:8080/medic/create-medical-history`,medical_history);
    }

    logoutMedic(): Observable<any> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', token);
        }
        return this.http.post('http://localhost:8080/medic/logout', {}, { headers });
    }

    public updateStatus(medicine:any){
        return this.http.post(`http://localhost:8080/medic/createMedicalHistory`,medicine);
    }
}
