import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import baserUrl from './helper';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  public añadirUsuario(user:any){
    return this.http.post(`http://localhost:8080/patient/register`,user);
  }
}
