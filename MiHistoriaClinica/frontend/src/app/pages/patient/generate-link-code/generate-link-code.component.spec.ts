import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GenerateLinkCodeComponent } from './generate-link-code.component';

describe('GenerateLinkCodeComponent', () => {
  let component: GenerateLinkCodeComponent;
  let fixture: ComponentFixture<GenerateLinkCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenerateLinkCodeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateLinkCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
