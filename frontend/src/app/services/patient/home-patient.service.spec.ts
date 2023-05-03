import { TestBed } from '@angular/core/testing';
import {HomePatientService} from "./home-patient.service";

describe('HomePatientService', () => {
    let service: HomePatientService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(HomePatientService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
