import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalHistoryListMedicComponent } from './medical-history-list-medic.component';

describe('MedicalHistoryListMedicComponent', () => {
  let component: MedicalHistoryListMedicComponent;
  let fixture: ComponentFixture<MedicalHistoryListMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalHistoryListMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicalHistoryListMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
