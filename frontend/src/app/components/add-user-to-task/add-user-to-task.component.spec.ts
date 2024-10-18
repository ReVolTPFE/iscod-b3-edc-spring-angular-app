import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserToTaskComponent } from './add-user-to-task.component';

describe('AddUserToTaskComponent', () => {
  let component: AddUserToTaskComponent;
  let fixture: ComponentFixture<AddUserToTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddUserToTaskComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddUserToTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
