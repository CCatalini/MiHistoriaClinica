import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMedicalHistoryComponent } from './edit-medical-history.component';

describe('EditMedicalHistoryComponent', () => {
  let component: EditMedicalHistoryComponent;
  let fixture: ComponentFixture<EditMedicalHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditMedicalHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditMedicalHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
