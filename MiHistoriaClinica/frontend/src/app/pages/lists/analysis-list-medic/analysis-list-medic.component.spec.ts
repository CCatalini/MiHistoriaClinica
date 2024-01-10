import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisListMedicComponent } from './analysis-list-medic.component';

describe('AnalysisListMedicComponent', () => {
  let component: AnalysisListMedicComponent;
  let fixture: ComponentFixture<AnalysisListMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnalysisListMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalysisListMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
