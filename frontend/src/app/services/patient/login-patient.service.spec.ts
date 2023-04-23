import { TestBed } from '@angular/core/testing';
import {LoginPatientService} from "./login-patient.service";

describe('PatientService', () => {
    let service: LoginPatientService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(LoginPatientService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
