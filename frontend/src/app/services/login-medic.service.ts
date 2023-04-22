import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class LoginMedicService {

    constructor(private http:HttpClient) { }

    public loginMedic(medic:any){
        return this.http.post(`http://localhost:8080/medic/login`,medic);
    }

}
