import { TestBed } from '@angular/core/testing';
import {LinkPatientService} from "./link-patient.service";

describe('MedicService', () => {
    let service: LinkPatientService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(LinkPatientService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
