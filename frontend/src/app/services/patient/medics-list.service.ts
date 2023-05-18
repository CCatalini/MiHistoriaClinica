import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class MedicsListService {

    constructor(private http: HttpClient) { }

    public getMedicsList() {
        return this.http.get('http://localhost:8080/medic/getAll', {});
    }
}
