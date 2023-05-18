import { TestBed } from '@angular/core/testing';
import {MedicinesListService} from "./medicines-list.service";

describe('MedicineListService', () => {
    let service: MedicinesListService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(MedicinesListService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
