import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupMedicComponent } from './signup-medic.component';

describe('SignupMedicComponent', () => {
  let component: SignupMedicComponent;
  let fixture: ComponentFixture<SignupMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignupMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignupMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
