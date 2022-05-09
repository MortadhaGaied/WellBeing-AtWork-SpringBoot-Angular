import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QVTComponent } from './qvt.component';

describe('QVTComponent', () => {
  let component: QVTComponent;
  let fixture: ComponentFixture<QVTComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QVTComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QVTComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
