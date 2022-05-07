import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatroomsTableComponent } from './chatrooms-table.component';

describe('ChatroomsTableComponent', () => {
  let component: ChatroomsTableComponent;
  let fixture: ComponentFixture<ChatroomsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatroomsTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatroomsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
