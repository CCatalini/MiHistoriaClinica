import { TestBed } from '@angular/core/testing';
import {GenerateLinkCodeService} from "./generate-link-code.service";

describe('HomePatientService', () => {
    let service: GenerateLinkCodeService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(GenerateLinkCodeService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
