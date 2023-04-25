import { TestBed } from '@angular/core/testing';
import {AddAnalysisService} from "./analysis.service";

describe('AddAnalysisService', () => {
    let service: AddAnalysisService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(AddAnalysisService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
