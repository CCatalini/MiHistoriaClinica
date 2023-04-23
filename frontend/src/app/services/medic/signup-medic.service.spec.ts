import { TestBed } from '@angular/core/testing';
import { SignupMedicService } from './signup-medic.service';

describe('MedicService', () => {
  let service: SignupMedicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SignupMedicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
