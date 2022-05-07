import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeatailVoteComponent } from './deatail-vote.component';

describe('DeatailVoteComponent', () => {
  let component: DeatailVoteComponent;
  let fixture: ComponentFixture<DeatailVoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeatailVoteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeatailVoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
