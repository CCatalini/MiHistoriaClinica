import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinesListMedicComponent } from './medicines-list-medic.component';

describe('MedicinesListMedicComponent', () => {
  let component: MedicinesListMedicComponent;
  let fixture: ComponentFixture<MedicinesListMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicinesListMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicinesListMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
