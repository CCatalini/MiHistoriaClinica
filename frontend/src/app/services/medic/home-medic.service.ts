import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class HomeMedicService {

    constructor(private http:HttpClient) { }

}
