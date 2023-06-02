import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinesListPatientComponent } from './medicines-list-patient.component';

describe('MedicinesListComponent', () => {
  let component: MedicinesListPatientComponent;
  let fixture: ComponentFixture<MedicinesListPatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicinesListPatientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicinesListPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
