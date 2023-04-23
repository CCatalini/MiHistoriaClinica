import { TestBed } from '@angular/core/testing';
import {HomeService} from "./login-medic.service";

describe('HomeService', () => {
    let service: HomeService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(HomeService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
