import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupPacientComponent } from './signup-pacient.component';

describe('SignupComponent', () => {
  let component: SignupPacientComponent;
  let fixture: ComponentFixture<SignupPacientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignupPacientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignupPacientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
