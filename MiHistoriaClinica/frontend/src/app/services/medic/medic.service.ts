import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {map, Observable, throwError} from "rxjs";
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
        return this.http.post('http://localhost:8080/medicine/medic/create-medicine', medicine, { headers });
    }

    public addAnalysis(analysis: any) {
        const token = localStorage.getItem('token');
        const patientLinkCode = localStorage.getItem('patientLinkCode') || '';
        if (!patientLinkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return throwError('No se proporcionó el código de enlace del paciente.');
        }
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
            headers = headers.set('patientLinkCode', patientLinkCode);
        }
        return this.http.post(`http://localhost:8080/analysis/medic/create-patient-analysis`, analysis, { headers });
    }

    public addMedicalAppointment(appointment: any) {
        const token = localStorage.getItem('token');
        const patientLinkCode = localStorage.getItem('patientLinkCode') || '';
        if (!patientLinkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return throwError('No se proporcionó el código de enlace del paciente.');
        }
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', "Bearer " + token);
            headers = headers.set('patientLinkCode', patientLinkCode);
        }
        return this.http.post(`http://localhost:8080/medicalAppointment/medic/create`, appointment, { headers });
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
        return this.http.post('http://localhost:8080/medical-file/medic/create', medicalHistoryModel, {headers});
    }

    public updateMedicalHistory(medicalHistoryModel: any) {
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
        return this.http.post('http://localhost:8080/medical-file/medic/update', medicalHistoryModel, {headers});
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

    public getMedicinesList() {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        if (!linkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return;
        }   let headers = new HttpHeaders();
        headers = headers.set('patientLinkCode', linkCode);
        return this.http.get('http://localhost:8080/medicine/medic/get-patient-medicines', { headers });
    }

    getMedicinesByStatus(status: string) {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        let headers = new HttpHeaders().set('patientLinkCode', linkCode);
        let params = new HttpParams().set("status", status);
        return this.http.get<any[]>("http://localhost:8080/medicine/medic/get-medicines-byStatus", { headers: headers, params: params });
    }

    getAnalysisByStatus(status: string) {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        let headers = new HttpHeaders().set('patientLinkCode', linkCode);
        let params = new HttpParams().set("status", status);
        return this.http.get<any[]>("http://localhost:8080/analysis/medic/get-analysis-byStatus", { headers: headers, params: params });
    }

    public getAnalysisList() {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        if (!linkCode) {
            Swal.fire(
                'Error',
                'No se proporcionó el código de enlace del paciente.',
                'error'
            );
            return;
        }   let headers = new HttpHeaders();
        headers = headers.set('patientLinkCode', linkCode);
        return this.http.get('http://localhost:8080/analysis/medic/get-analysis', {headers});
    }

    public getAppointmentsList(): Observable<any[]>{
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        let headers = new HttpHeaders();
        headers = headers.set('patientLinkCode', linkCode);
        return this.http.get<any[]>('http://localhost:8080/medicalAppointment/medic/get', {headers: headers});
    }

    logoutMedic(): Observable<any> {
        const token = localStorage.getItem('token');
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }
        return this.http.post('http://localhost:8080/medic/logout', {}, { headers });
    }

    public getPatientMedicalHistory(): Observable<string> {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        let headers = new HttpHeaders();
        headers = headers.set('patientLinkCode', linkCode);
        return this.http.get<string>('http://localhost:8080/medical-file/medic/get', {headers: headers});
    }

    public deleteMedicine(medicineId: number) {
        const linkCode = localStorage.getItem('patientLinkCode') || '';
        const params = new HttpParams().set('medicineId', medicineId.toString());

        let headers = new HttpHeaders()
                            .set('patientLinkCode', linkCode);

        return this.http.delete('http://localhost:8080/medicine/medic/delete-medicine', {params: params,headers: headers})
            .pipe(
                map(() => {
                    Swal.fire(
                        'Eliminado',
                        'El medicamento ha sido eliminado correctamente.',
                        'success'
                    );
                })
            );
    }

    public deleteAnalysis(analysis_id: number) {
        const linkCode = localStorage.getItem('patientLinkCode') || '';

        const params = new HttpParams().set('analysisId', analysis_id.toString());
        let headers = new HttpHeaders().set('patientLinkCode', linkCode);

        return this.http.delete('http://localhost:8080/analysis/medic/delete-analysis', {params: params,headers: headers})
            .pipe(
                map(() => {
                    Swal.fire(
                        'Eliminado',
                        'El estudio ha sido eliminado correctamente.',
                        'success'
                    );
                })
            );
    }

    public getAllMedicineNames() {
        return this.http.get<string[]>('http://localhost:8080/medicine/all-names');
    }

    getMedicineDescription(medicineName: string): Observable<string> {
        let params = new HttpParams().set("medicineName", medicineName);
        return this.http.get('http://localhost:8080/medicine/description', { params, responseType: 'text' });
    }

    getAllAnalysisNames() {
        return this.http.get<string[]>('http://localhost:8080/analysis/all-names');
    }

    getAllMedicalCenterNames() {
        return this.http.get<string[]>('http://localhost:8080/analysis/medicalCenter/all-names');
    }

    getAnalysisDescription(analysisName: string): Observable<string> {
        let params = new HttpParams().set("analysisName", analysisName);
        return this.http.get('http://localhost:8080/analysis/description', { params, responseType: 'text' });
    }

    getSpecialtyOptions() {
        return this.http.get<string[]>('http://localhost:8080/medic/all-specialties');
    }

    getBloodTypes() {
        return this.http.get<string[]>('http://localhost:8080/medical-file/blood-types');
    }
}


