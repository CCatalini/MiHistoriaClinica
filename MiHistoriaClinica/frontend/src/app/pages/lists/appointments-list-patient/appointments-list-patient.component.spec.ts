import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentsListPatientComponent } from './appointments-list-patient.component';

describe('AppointmentsListPatientComponent', () => {
  let component: AppointmentsListPatientComponent;
  let fixture: ComponentFixture<AppointmentsListPatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointmentsListPatientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentsListPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
