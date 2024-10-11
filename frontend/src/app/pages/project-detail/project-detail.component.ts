import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {forkJoin} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
  users: Record<any, any>;
}

interface User {
  id: number;
  username: string;
  email: string;
}

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.scss']
})
export class ProjectDetailComponent {
  addUserInProjectForm: FormGroup;
  submitted = false;
  error = '';

  protected project?: Project;
  protected tasks?: Record<any, any>;
  protected users?: User[];
  protected usersNotInProject: User[] = [];
  projectId = Number(this.route.snapshot.paramMap.get('projectId'));

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {
    this.addUserInProjectForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
    this.loadData(this.projectId)
  }

  //to prevent loading of users before projects
  loadData(projectId: number) {
    forkJoin({
      project: this.projectService.getProject(projectId),
      tasks: this.projectService.getProjectTasks(projectId)
    }).subscribe({
      next: (response) => {
        this.project = response.project;
        this.tasks = response.tasks;

        this.getUsers();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getProject(projectId: number) {
    this.projectService.getProject(projectId).subscribe({
      next: (response) => {
        this.project = response;
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getProjectTasks(projectId: number) {
    this.projectService.getProjectTasks(projectId).subscribe({
      next: (response) => {
        this.tasks = response;
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getLastTaskHistory(task: any) {
    return task.taskHistories.length > 0 ? task.taskHistories[task.taskHistories.length - 1] : null;
  }

  getUsers() {
    this.userService.getUsers().subscribe({
      next: (response) => {
        this.users = response;
        this.usersNotInProject = this.filterUsersNotInProject();
        console.log(this.usersNotInProject);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  filterUsersNotInProject() {
    // @ts-ignore
    return this.users.filter(user => {
      // @ts-ignore
      return !this.project.users.some(projectUser => projectUser.userId === user.id);
    });
  }

  get f() { return this.addUserInProjectForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.addUserInProjectForm.invalid) {
      return;
    }

    const formEmail = this.f['email'].value;

    this.projectService.addUserInProject(this.projectId, formEmail).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
