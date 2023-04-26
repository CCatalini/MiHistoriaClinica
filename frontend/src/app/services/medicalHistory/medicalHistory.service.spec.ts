import { TestBed } from '@angular/core/testing';
import {CreateMedicalHistoryService} from "./medicalHistory.service";

describe('CreateMedicalHistoryService', () => {
    let service: CreateMedicalHistoryService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(CreateMedicalHistoryService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
