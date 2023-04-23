import { TestBed } from '@angular/core/testing';
import {LoginMedicService} from "./login-medic.service";

describe('MedicService', () => {
    let service: LoginMedicService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(LoginMedicService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
