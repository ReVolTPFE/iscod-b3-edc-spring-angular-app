import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserToTaskComponent } from './add-user-to-task.component';
import {ReactiveFormsModule} from "@angular/forms";
import {ProjectService} from "../../services/project.service";
import {ActivatedRoute} from "@angular/router";
import {of, throwError} from "rxjs";

describe('AddUserToTaskComponent', () => {
  let component: AddUserToTaskComponent;
  let fixture: ComponentFixture<AddUserToTaskComponent>;

  let projectServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      assignUserInTask: jest.fn(),
    };

    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: (key: string) => {
            if (key === 'projectId' || key === 'taskId') {
              return '1';
            }
            return null;
          }
        }
      }
    };

    await TestBed.configureTestingModule({
      declarations: [ AddUserToTaskComponent ],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddUserToTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should mark form as submitted on submit', () => {
    component.onSubmitAssignUser();
    expect(component.submittedAssign).toBe(true);
  });

  it('should not call projectService.assignUserInTask if form is invalid', () => {
    component.assignUserToTheTaskForm.controls['userId'].setValue('');
    component.onSubmitAssignUser();
    expect(projectServiceMock.assignUserInTask).not.toHaveBeenCalled();
  });

  it('should call projectService.assignUserInTask with correct parameters when form is valid', () => {
    const userIdParam = 1;
    component.assignUserToTheTaskForm.controls['userId'].setValue(userIdParam);

    projectServiceMock.assignUserInTask.mockReturnValue(of({}));

    component.onSubmitAssignUser();

    expect(projectServiceMock.assignUserInTask).toHaveBeenCalledWith(
      userIdParam,
      component.projectId,
      component.taskId,
    );
  });
});
