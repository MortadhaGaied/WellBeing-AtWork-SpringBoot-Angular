import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCollabComponent } from './new-collab.component';

describe('NewCollabComponent', () => {
  let component: NewCollabComponent;
  let fixture: ComponentFixture<NewCollabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCollabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCollabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
