import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
}

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {
  createProjectForm: FormGroup;
  submitted = false;
  error = '';
  projects: Project[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService
  ) {
    this.getProjects();

    this.createProjectForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', Validators.required],
      startedAt: ['', Validators.required],
    });
  }

  get f() { return this.createProjectForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.createProjectForm.invalid) {
      return;
    }

    const formData = {
      name: this.f['name'].value,
      description: this.f['description'].value,
      startedAt: this.f['startedAt'].value // format YYYY-MM-DD
    };

    this.projectService.createProject(formData).subscribe({
      next: () => {
        this.getProjects();
      },
      error: (error) => {
        this.error = error.error?.message || 'An error occurred during project creation';
      }
    });
  }

  getProjects() {
    this.projectService.getProjects().subscribe({
      next: (response) => {
        this.projects = [];

        response.forEach((project: Project) => {
          this.projects.push(project);
        });
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
