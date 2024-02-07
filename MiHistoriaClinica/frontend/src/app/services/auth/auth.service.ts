import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) {}

    registerPatient(patient: any): Observable<any> {
        return this.http.post<any>(`${this.apiUrl}/patient/register`, patient);
    }

    confirmAccount(token: string): Observable<any> {
        let headers = new HttpHeaders();
        if(token) {
            headers = headers.set('Authorization', 'Bearer ' + token);
        }

        return this.http.get<any>(`${this.apiUrl}/patient/confirm-account`, { headers: headers });
    }

    getTokenFromLocalStorage(): string | null {
        return localStorage.getItem('token');
    }
}
