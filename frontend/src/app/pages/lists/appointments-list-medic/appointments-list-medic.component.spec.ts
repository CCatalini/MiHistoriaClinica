import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentsListMedicComponent } from './appointments-list-medic.component';

describe('AppointmentsListMedicComponent', () => {
  let component: AppointmentsListMedicComponent;
  let fixture: ComponentFixture<AppointmentsListMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointmentsListMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentsListMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
