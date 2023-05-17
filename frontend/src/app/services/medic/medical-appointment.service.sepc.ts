import { TestBed } from '@angular/core/testing';
import {MedicalAppointmentService} from "./medical-appointment.service";

describe('MedicalAppointmentService', () => {
    let service: MedicalAppointmentService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(MedicalAppointmentService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
