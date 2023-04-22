import { TestBed } from '@angular/core/testing';
import {SignupPatientService} from "./signup-patient.service";

describe('PatientService', () => {
    let service: SignupPatientService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(SignupPatientService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
