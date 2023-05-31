import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

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

}
