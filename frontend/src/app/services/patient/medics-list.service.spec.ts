import { TestBed } from '@angular/core/testing';
import {MedicsListService} from "./medics-list.service";

describe('PatientsListService', () => {
    let service: MedicsListService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(MedicsListService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
