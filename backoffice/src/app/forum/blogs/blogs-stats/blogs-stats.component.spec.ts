import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogsStatsComponent } from './blogs-stats.component';

describe('BlogsStatsComponent', () => {
  let component: BlogsStatsComponent;
  let fixture: ComponentFixture<BlogsStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlogsStatsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlogsStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
