import { TestBed } from '@angular/core/testing';
import {AttendPatientService} from "./attend-patient.component";

describe('AttendPatientService', () => {
    let service: AttendPatientService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(AttendPatientService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
