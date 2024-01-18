import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicCalendarComponent } from './medic-calendar.component';

describe('MedicCalendarComponent', () => {
  let component: MedicCalendarComponent;
  let fixture: ComponentFixture<MedicCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicCalendarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
