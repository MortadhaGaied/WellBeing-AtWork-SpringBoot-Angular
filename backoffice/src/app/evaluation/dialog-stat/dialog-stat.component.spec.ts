import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogStatComponent } from './dialog-stat.component';

describe('DialogStatComponent', () => {
  let component: DialogStatComponent;
  let fixture: ComponentFixture<DialogStatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogStatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
