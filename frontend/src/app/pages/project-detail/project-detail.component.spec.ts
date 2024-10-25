import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectDetailComponent } from './project-detail.component';
import {ProjectService} from "../../services/project.service";
import {ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {of} from "rxjs";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
  users: { userId: number, username: string, email: string, role: string }[];
}

interface User {
  id: number;
  username: string;
  email: string;
}

describe('ProjectDetailComponent', () => {
  let component: ProjectDetailComponent;
  let fixture: ComponentFixture<ProjectDetailComponent>;

  let projectServiceMock: any;
  let userServiceMock: any;
  let authServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      getProject: jest.fn(),
      getProjectTasks: jest.fn(),
      addUserInProject: jest.fn(),
    };

    userServiceMock = {
      getUsers: jest.fn(),
    };

    authServiceMock = {
      getUserId: jest.fn(),
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
      declarations: [ ProjectDetailComponent ],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
      ],
      imports: [ReactiveFormsModule],
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectDetailComponent);
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

    const mockTasks = [{ id: 1, name: 'Task 1' }, { id: 2, name: 'Task 2' }];

    projectServiceMock.getProject.mockReturnValue(of(mockProject));
    projectServiceMock.getProjectTasks.mockReturnValue(of(mockTasks));
    userServiceMock.getUsers.mockReturnValue(of([]));

    component.loadData(1);

    expect(projectServiceMock.getProject).toHaveBeenCalledWith(1);
    expect(projectServiceMock.getProjectTasks).toHaveBeenCalledWith(1);
    expect(component.project).toEqual(mockProject);
    expect(component.tasks).toEqual(mockTasks);
  });

  it('should filter users not in the project', () => {
    component.users = [
      { id: 1, username: 'User1', email: 'user1@example.com' },
      { id: 2, username: 'User2', email: 'user2@example.com' },
    ];

    component.project = {
      id: 1,
      name: 'Project 1',
      description: 'Desc 1',
      startedAt: '2024-01-01',
      users: [
        { userId: 1, username: 'User1', email: 'user1@example.com', role: 'ADMIN' },
      ],
    };

    const filteredUsers = component.filterUsersNotInProject();

    expect(filteredUsers.length).toBe(1);
    expect(filteredUsers[0].id).toBe(2);
  });

  it('should add user to project when form is valid', () => {
    component.addUserInProjectForm.controls['email'].setValue('user2@example.com');

    projectServiceMock.addUserInProject.mockReturnValue(of({}));

    component.onSubmit();

    expect(projectServiceMock.addUserInProject).toHaveBeenCalledWith(1, 'user2@example.com');
  });

  it('should not add user to project if form is invalid', () => {
    component.addUserInProjectForm.controls['email'].setValue('');

    component.onSubmit();

    expect(projectServiceMock.addUserInProject).not.toHaveBeenCalled();
  });



  it('should get the last task history if task has histories', () => {
    const task = {
      taskHistories: [
        { name: 'Task Started', description: 'Task has been started.', status: 'ONGOING', priority: 'HIGH', dueDate: '2024-10-01', endedAt: null },
        { name: 'Task Completed', description: 'Task has been completed.', status: 'DONE', priority: 'LOW', dueDate: '2024-10-15', endedAt: '2024-10-15' },
      ]
    };

    const lastHistory = component.getLastTaskHistory(task);
    expect(lastHistory).toEqual(task.taskHistories[task.taskHistories.length - 1]);
  });

  it('should get project tasks on task creation', () => {
    const projectId = 1;

    component.onTaskCreated();

    expect(projectServiceMock.getProjectTasks).toHaveBeenCalledWith(projectId);
  });

  it('should return null if task has no histories', () => {
    const task = { taskHistories: [] };

    const lastHistory = component.getLastTaskHistory(task);
    expect(lastHistory).toBeNull();
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
});
