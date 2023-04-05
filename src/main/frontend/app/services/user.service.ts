import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { }
  public registrarPaciente(user:any){
    return this.httpClient.post(`&{baseUrl}/usuarios`,user);
  }
}
