import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SujetThumbnailComponent } from './sujet-thumbnail.component';

describe('SujetThumbnailComponent', () => {
  let component: SujetThumbnailComponent;
  let fixture: ComponentFixture<SujetThumbnailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SujetThumbnailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SujetThumbnailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
