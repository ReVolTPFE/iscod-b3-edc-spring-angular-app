import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskComponent } from './task.component';
import {ProjectService} from "../../services/project.service";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {of} from "rxjs";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
  users: { userId: number, username: string, email: string, role: string }[];
}

interface TaskHistory {
  name: string;
  description: string;
  status: string;
  priority: string;
  dueDate: string;
  endedAt: string|null;
}

interface Task {
  id: number;
  projectId: number;
  taskHistories: TaskHistory[];
  users: { userId: number, username: string, email: string}[];
}

interface User {
  userId: number;
  username: string;
  email: string;
  role: string;
}

describe('TaskComponent', () => {
  let component: TaskComponent;
  let fixture: ComponentFixture<TaskComponent>;

  let projectServiceMock: any;
  let authServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      getProject: jest.fn(),
      getTask: jest.fn(),
      editTaskInProject: jest.fn(),
    };

    authServiceMock = {
      getUserId: jest.fn(),
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
      declarations: [ TaskComponent ],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
      ],
      imports: [ReactiveFormsModule],
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load project and tasks on initialization', () => {
    const mockProject: Project = {
      id: 1,
      name: 'Project 1',
      description: 'Desc 1',
      startedAt: '2024-01-01',
      users: [],
    };

    const mockTask: Task = { id: 1, projectId: 1, taskHistories: [], users: [] };

    projectServiceMock.getProject.mockReturnValue(of(mockProject));
    projectServiceMock.getTask.mockReturnValue(of(mockTask));

    component.loadData(1);

    expect(projectServiceMock.getProject).toHaveBeenCalledWith(1);
    expect(projectServiceMock.getTask).toHaveBeenCalledWith(1, 1);
    expect(component.project).toEqual(mockProject);
    expect(component.task).toEqual(mockTask);
    expect(component.usersNotInTask).toEqual([]);
  });

  it('should edit task when form is valid', () => {
    component.editTaskForm.controls['name'].setValue('Task 1');
    component.editTaskForm.controls['description'].setValue('Desc 1');
    component.editTaskForm.controls['status'].setValue('TODO');
    component.editTaskForm.controls['priority'].setValue('LOW');
    component.editTaskForm.controls['dueDate'].setValue('2024-10-10');
    component.editTaskForm.controls['endedAt'].setValue(null);

    const taskDataMock = {
      name: 'Task 1',
      description: 'Desc 1',
      status: 'TODO',
      priority: 'LOW',
      dueDate: '2024-10-10',
      endedAt: null
    };

    projectServiceMock.editTaskInProject.mockReturnValue(of({}));

    component.onSubmit();

    expect(projectServiceMock.editTaskInProject).toHaveBeenCalledWith(1, 1, taskDataMock);
  });

  it('should not edit task if form is invalid', () => {
    component.editTaskForm.controls['name'].setValue('');

    component.onSubmit();

    expect(projectServiceMock.editTaskInProject).not.toHaveBeenCalled();
  });

  it('should get the last task history if task has histories', () => {
    const task: Task = {
      id: 1,
      projectId: 1,
      users: [],
      taskHistories: [
        { name: 'Task Started', description: 'Task has been started.', status: 'ONGOING', priority: 'HIGH', dueDate: '2024-10-01', endedAt: null },
        { name: 'Task Completed', description: 'Task has been completed.', status: 'DONE', priority: 'LOW', dueDate: '2024-10-15', endedAt: '2024-10-15' },
      ]
    };

    const lastHistory = component.getLastTaskHistory(task);
    expect(lastHistory).toEqual(task.taskHistories[task.taskHistories.length - 1]);
  });

  it('should be admin user', () => {
    const mockProject: Project = {
      id: 1,
      name: 'Project 1',
      description: 'Desc 1',
      startedAt: '2024-01-01',
      users: [
        { userId: 1, username: 'AdminUser', email: 'admin@example.com', role: 'ADMIN' },
        { userId: 2, username: 'RegularUser', email: 'user@example.com', role: 'MEMBER' }
      ]
    };

    authServiceMock.getUserId.mockReturnValue(1);

    component.isUserAdminOrMember(mockProject);

    expect(component.userAdmin).toBe(true);
  });

  it('should be regular user', () => {
    const mockProject: Project = {
      id: 1,
      name: 'Project 1',
      description: 'Desc 1',
      startedAt: '2024-01-01',
      users: [
        { userId: 1, username: 'AdminUser', email: 'admin@example.com', role: 'ADMIN' },
        { userId: 2, username: 'RegularUser', email: 'user@example.com', role: 'MEMBER' }
      ]
    };

    authServiceMock.getUserId.mockReturnValue(2);

    component.isUserAdminOrMember(mockProject);

    expect(component.userMember).toBe(true);
  });

  it('should filter users not in the task', () => {
    component.task = {
      id: 1,
      projectId: 1,
      users: [],
      taskHistories: []
    };

    component.project = {
      id: 1,
      name: 'Project 1',
      description: 'Desc 1',
      startedAt: '2024-01-01',
      users: [
        { userId: 1, username: 'AdminUser', email: 'admin@example.com', role: 'ADMIN' },
        { userId: 2, username: 'AdminUser2', email: 'admin2@example.com', role: 'ADMIN' },
      ],
    };

    const filteredUsers = component.filterUsersNotInTask();

    expect(filteredUsers.length).toBe(2);
    expect(filteredUsers[0].userId).toBe(1);
    expect(filteredUsers[1].userId).toBe(2);
  });

  it('should get project', () => {
    const mockProject: Project = {
      id: 1,
      name: 'Project 1',
      description: 'Desc 1',
      startedAt: '2024-01-01',
      users: [],
    };

    projectServiceMock.getProject.mockReturnValue(of(mockProject));

    component.getProject(1);

    expect(projectServiceMock.getProject).toHaveBeenCalledWith(1);
    expect(component.project).toEqual(mockProject);
  });
});
