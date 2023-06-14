import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import Swal from "sweetalert2";

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

    public addMedicine(medicine: any) {
        const token = localStorage.getItem('token');
        const patientLinkCode = localStorage.getItem('patientLinkCode') || '';
        if (!patientLinkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return;
        }   let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
            headers = headers.set('patientLinkCode', patientLinkCode);
        }
        return this.http.post('http://localhost:8080/medic/create-medicine', medicine, { headers });
    }


    public createMedicalHistory(medicalHistoryModel: any) {
        const token = localStorage.getItem('token');
        const patientLinkCode = localStorage.getItem('patientLinkCode') || '';
        if (!patientLinkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return;
        }   let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
            headers = headers.set('patientLinkCode', patientLinkCode);
        }
        return this.http.post('http://localhost:8080/medic/create-medical-history', medicalHistoryModel, {headers});
    }

    public getPatientsList(token: string): Observable<any[]> {
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.get<any[]>('http://localhost:8080/medic/get-patients', { headers: headers });
    }

    public addMedic(medic:any){
        return this.http.post(`http://localhost:8080/medic/signup`,medic);
    }

    public getMedicinesList(patientLinkCode: string) {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        if (!patientLinkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return;
        }   let headers = new HttpHeaders();
        headers = headers.set('patientLinkCode', linkCode);
        return this.http.get('http://localhost:8080/medic/get-patient-medicines', { headers });
    }


    public getAnalysisList() {
        return this.http.get('http://localhost:8080/analysis/findAllAnalysis', {});
    }

    public addAnalysis(analysis:any){
        return this.http.post(`http://localhost:8080/medic/addAnalysis`,analysis);
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
