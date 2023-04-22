import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignupMedicService {

  constructor(private http:HttpClient) { }

  public addMedic(medic:any){
    return this.http.post(`http://localhost:8080/medic/signup`,medic);
  }

}
