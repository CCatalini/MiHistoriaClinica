import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  public addPatient(patient:any){
    return this.http.post(`http://localhost:8080/patient/signup`,patient);
  }
  public addMedic(medic:any){
    return this.http.post(`http://localhost:8080/medic/signup`,medic);
  }
}
