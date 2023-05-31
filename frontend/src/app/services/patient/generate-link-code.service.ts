import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class GenerateLinkCodeService{

    constructor(private http:HttpClient) { }

    public generateLinkCode(patient:any){
        return this.http.post(`http://localhost:8080/patient/{patientId}/generate-link-code`,patient);
    }

}
