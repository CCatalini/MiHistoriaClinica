import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllMedicsListComponent } from './all-medics-list.component';

describe('AllMedicsListComponent', () => {
  let component: AllMedicsListComponent;
  let fixture: ComponentFixture<AllMedicsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllMedicsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllMedicsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
