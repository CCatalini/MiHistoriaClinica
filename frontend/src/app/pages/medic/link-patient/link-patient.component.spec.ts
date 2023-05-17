import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkPatientComponent } from './link-patient.component';

describe('LinkPatientComponent', () => {
  let component: LinkPatientComponent;
  let fixture: ComponentFixture<LinkPatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LinkPatientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LinkPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
