import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisListPatientComponent } from './analysis-list-patient.component';

describe('AnalysisListPatientComponent', () => {
  let component: AnalysisListPatientComponent;
  let fixture: ComponentFixture<AnalysisListPatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnalysisListPatientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalysisListPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
