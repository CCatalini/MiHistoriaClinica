import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeMedicComponent } from './home-medic.component';

describe('HomeMedicComponent', () => {
  let component: HomeMedicComponent;
  let fixture: ComponentFixture<HomeMedicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeMedicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeMedicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
