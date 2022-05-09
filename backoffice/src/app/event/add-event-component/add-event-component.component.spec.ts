import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEventComponentComponent } from './add-event-component.component';

describe('AddEventComponentComponent', () => {
  let component: AddEventComponentComponent;
  let fixture: ComponentFixture<AddEventComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEventComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEventComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
