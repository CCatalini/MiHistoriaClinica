import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class CreateMedicalHistoryService {

    constructor(private http:HttpClient) { }

    public createMedicalHistory(medicalHistory:any){
        return this.http.post(`http://localhost:8080/medic/medicalHistory`,medicalHistory);
    }

}
