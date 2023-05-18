import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class MedicinesListService {

    constructor(private http: HttpClient) { }

    public getMedicinesList() {
        return this.http.get('http://localhost:8080/medicine/findAllMedicine', {});
    }
}
