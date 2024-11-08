import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTaskComponent } from './add-task.component';
import {ReactiveFormsModule} from "@angular/forms";
import {ProjectService} from "../../services/project.service";
import {of, throwError} from "rxjs";
import {ActivatedRoute} from "@angular/router";

interface FillFormOverrides {
  name?: string;
  description?: string;
  status?: string;
  priority?: string;
  dueDate?: Date;
}

describe('AddTaskComponent', () => {
  let component: AddTaskComponent;
  let fixture: ComponentFixture<AddTaskComponent>;

  let projectServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      addTaskInProject: jest.fn(),
    };

    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: (key: string) => {
            if (key === 'projectId') {
              return '1';
            }
            return null;
          }
        }
      }
    };

    await TestBed.configureTestingModule({
      declarations: [ AddTaskComponent ],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  const fillForm = (overrides: FillFormOverrides = {}) => {
    component.addTaskForm.controls['name'].setValue(overrides.name || 'Task Name');
    component.addTaskForm.controls['description'].setValue(overrides.description || 'Task Description');
    component.addTaskForm.controls['status'].setValue(overrides.status || 'In Progress');
    component.addTaskForm.controls['priority'].setValue(overrides.priority || 'High');
    component.addTaskForm.controls['dueDate'].setValue(overrides.dueDate || '2024-12-31');
  };

  it('should create', () => {
    expect(component).toBeFalse();
  });

  it('should mark form as submitted on submit', () => {
    component.onSubmit();
    expect(component.submitted).toBe(true);
  });

  it('should not call projectService.addTaskInProject if form is invalid', () => {
    component.addTaskForm.controls['name'].setValue('');
    component.onSubmit();
    expect(projectServiceMock.addTaskInProject).not.toHaveBeenCalled();
  });

  it('should call projectService.addTaskInProject with correct parameters when form is valid', () => {
    fillForm();

    projectServiceMock.addTaskInProject.mockReturnValue(of({}));

    component.onSubmit();

    expect(projectServiceMock.addTaskInProject).toHaveBeenCalledWith(
      component.projectId,
      {
        name: 'Task Name',
        description: 'Task Description',
        status: 'In Progress',
        priority: 'High',
        dueDate: '2024-12-31',
        endedAt: null
      }
    );
  });

  it('should set error message on service failure', () => {
    fillForm();

    projectServiceMock.addTaskInProject.mockReturnValue(throwError(() => ({
      error: { message: 'Task creation failed' }
    })));

    component.onSubmit();

    expect(component.error).toBe('Task creation failed');
  });
});
