import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AddAnalysisService {

    constructor(private http:HttpClient) { }

    public addAnalysis(analysis:any){
        return this.http.post(`http://localhost:8080/medic/analysis`,analysis);
    }

}
