import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PointsAndGiftsComponent } from './points-and-gifts.component';

describe('PointsAndGiftsComponent', () => {
  let component: PointsAndGiftsComponent;
  let fixture: ComponentFixture<PointsAndGiftsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PointsAndGiftsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointsAndGiftsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
