import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginMedicComponent } from './login-medic.component';

describe('LoginMedicComponent', () => {
  let component: LoginMedicComponent;
  let fixture: ComponentFixture<LoginMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
