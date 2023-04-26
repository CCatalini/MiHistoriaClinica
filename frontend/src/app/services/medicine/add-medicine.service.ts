    import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AddMedicineService {

    constructor(private http:HttpClient) { }

    public addMedicine(medicine:any){
        return this.http.post(`http://localhost:8080/medic/addMedicine`,medicine);
    }

}
