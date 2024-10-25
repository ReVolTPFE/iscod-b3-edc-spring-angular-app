import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectComponent } from './project.component';
import {ProjectService} from "../../services/project.service";
import {of} from "rxjs";
import {ReactiveFormsModule} from "@angular/forms";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
}

describe('ProjectComponent', () => {
  let component: ProjectComponent;
  let fixture: ComponentFixture<ProjectComponent>;

  let projectServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      createProject: jest.fn(),
      getProjects: jest.fn().mockReturnValue(of([])),
    };

    await TestBed.configureTestingModule({
      declarations: [ ProjectComponent ],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock }
      ],
      imports: [ReactiveFormsModule],
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should mark form as submitted on submit', () => {
    component.onSubmit();
    expect(component.submitted).toBe(true);
  });

  it('should not call projectService.createProject if form is invalid', () => {
    component.createProjectForm.controls['name'].setValue('');
    component.createProjectForm.controls['description'].setValue('');
    component.createProjectForm.controls['startedAt'].setValue('');
    component.onSubmit();
    expect(projectServiceMock.createProject).not.toHaveBeenCalled();
  });

  it('should call projectService.createProject with correct parameters when form is valid', () => {
    component.createProjectForm.controls['name'].setValue('Project 1');
    component.createProjectForm.controls['description'].setValue('Desc 1');
    component.createProjectForm.controls['startedAt'].setValue(new Date().toISOString().split('T')[0]);

    projectServiceMock.createProject.mockReturnValue(of({}));

    component.onSubmit();

    expect(projectServiceMock.createProject).toHaveBeenCalledWith(
      {
        name: 'Project 1',
        description: 'Desc 1',
        startedAt: new Date().toISOString().split('T')[0],
      }
    );
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
});
