import { TestBed } from '@angular/core/testing';
import {HomeMedicService} from "./home-medic.service";

describe('HomeMedicService', () => {
    let service: HomeMedicService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(HomeMedicService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
