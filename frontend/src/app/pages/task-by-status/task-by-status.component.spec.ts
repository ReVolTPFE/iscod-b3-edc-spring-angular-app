import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskByStatusComponent } from './task-by-status.component';
import {of} from "rxjs";
import {ProjectService} from "../../services/project.service";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
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
  projectName: string;
  taskHistories: TaskHistory[];
  users: { userId: number, username: string, email: string}[];
}

describe('TaskByStatusComponent', () => {
  let component: TaskByStatusComponent;
  let fixture: ComponentFixture<TaskByStatusComponent>;

  let projectServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      getProjects: jest.fn().mockReturnValue(of([])),
      getProjectTasks: jest.fn().mockReturnValue(of([])),
    };

    await TestBed.configureTestingModule({
      declarations: [ TaskByStatusComponent ],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskByStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should select the view', () => {
    const viewMock = 'ALL';
    component.selectView(viewMock);
    expect(component.selectedView).toEqual(viewMock);
  });

  it('should call projectService.getProjects and fill projects variable', () => {
    const mockProjects: Project[] = [
      { id: 1, name: 'Project 1', description: 'Desc 1', startedAt: new Date().toISOString().split('T')[0] },
      { id: 2, name: 'Project 2', description: 'Desc 2', startedAt: new Date().toISOString().split('T')[0] }
    ];

    projectServiceMock.getProjects.mockReturnValue(of(mockProjects));

    component.getProjects();

    expect(projectServiceMock.getProjects).toHaveBeenCalled();
    expect(component.projects.length).toBe(2);
    expect(component.projects).toEqual(mockProjects);
  });

  it('should call projectService.getProjectTasks and fill tasks variable', () => {
    const mockProject: Project = { id: 1, name: 'Project 1', description: 'Desc 1', startedAt: new Date().toISOString().split('T')[0] };

    const mockTasks: Task[] = [
      {
        id: 1,
        projectId: 1,
        projectName: "Project 1",
        taskHistories: [
          {
            name: "Initial Setup",
            description: "Set up the initial project structure.",
            status: "DONE",
            priority: "HIGH",
            dueDate: "2024-10-01",
            endedAt: "2024-10-01",
          },
          {
            name: "Development Phase",
            description: "Develop the core features of the application.",
            status: "ONGOING",
            priority: "MEDIUM",
            dueDate: "2024-11-15",
            endedAt: null,
          },
        ],
        users: [
          { userId: 1, username: "john_doe", email: "john@example.com" },
          { userId: 2, username: "jane_smith", email: "jane@example.com" },
        ],
      },
      {
        id: 2,
        projectId: 1,
        projectName: "Project 1",
        taskHistories: [
          {
            name: "Requirements Gathering",
            description: "Collect requirements from stakeholders.",
            status: "DONE",
            priority: "HIGH",
            dueDate: "2024-09-30",
            endedAt: "2024-09-30",
          },
          {
            name: "Testing Phase",
            description: "Testing the application for bugs and issues.",
            status: "TODO",
            priority: "LOW",
            dueDate: "2024-12-01",
            endedAt: null,
          },
        ],
        users: [
          { userId: 3, username: "alice_brown", email: "alice@example.com" },
          { userId: 4, username: "bob_johnson", email: "bob@example.com" },
        ],
      },
    ];


    projectServiceMock.getProjectTasks.mockReturnValue(of(mockTasks));

    component.getProjectTasks(mockProject);

    expect(projectServiceMock.getProjectTasks).toHaveBeenCalled();
    expect(component.tasks.length).toBe(2);
    expect(component.tasks).toEqual(mockTasks);
  });

  it('should get the last task history', () => {
    const mockTask: Task = {
      id: 1,
      projectId: 1,
      projectName: "Project 1",
      taskHistories: [
        {
          name: "Initial Setup",
          description: "Set up the initial project structure.",
          status: "DONE",
          priority: "HIGH",
          dueDate: "2024-10-01",
          endedAt: "2024-10-01",
        },
        {
          name: "Development Phase",
          description: "Develop the core features of the application.",
          status: "ONGOING",
          priority: "MEDIUM",
          dueDate: "2024-11-15",
          endedAt: null,
        },
      ],
      users: [
        { userId: 1, username: "john_doe", email: "john@example.com" },
        { userId: 2, username: "jane_smith", email: "jane@example.com" },
      ],
    };

    const mockTaskHistory: TaskHistory = {
      name: "Development Phase",
      description: "Develop the core features of the application.",
      status: "ONGOING",
      priority: "MEDIUM",
      dueDate: "2024-11-15",
      endedAt: null,
    };

    const result = component.getLastTaskHistory(mockTask);
    expect(result).toEqual(mockTaskHistory);
  });

  it('should return null when task has no histories', () => {
    const task: Task = {
      id: 1,
      projectId: 1,
      projectName: "Project 1",
      taskHistories: [],
      users: [
        { userId: 3, username: "alice_brown", email: "alice@example.com" },
        { userId: 4, username: "bob_johnson", email: "bob@example.com" },
      ],
    };

    const result = component.getLastTaskHistory(task);
    expect(result).toBeNull();
  });
});
