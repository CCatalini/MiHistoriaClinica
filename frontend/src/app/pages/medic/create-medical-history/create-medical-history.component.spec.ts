import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMedicalHistoryComponent } from './create-medical-history.component';

describe('CreateMedicalHistoryComponent', () => {
  let component: CreateMedicalHistoryComponent;
  let fixture: ComponentFixture<CreateMedicalHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMedicalHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMedicalHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
