import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendPatientComponent } from './attend-patient.component';

describe('AttendPatientComponent', () => {
  let component: AttendPatientComponent;
  let fixture: ComponentFixture<AttendPatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AttendPatientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttendPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
