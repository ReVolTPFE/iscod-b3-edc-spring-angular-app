import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {forkJoin} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";

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

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.scss']
})
export class ProjectDetailComponent {
  addUserInProjectForm: FormGroup;
  submitted = false;
  error = '';
  userAdmin = false;
  userMember = false;

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
    private authService: AuthService,
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
        this.isUserAdminOrMember(this.project);
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

  onTaskCreated() {
    this.projectService.getProjectTasks(this.projectId);
  }

  sortByRoleAndId(a: any, b: any): number {
    if (a.value.role < b.value.role) {
      return -1;
    }
    if (a.value.role > b.value.role) {
      return 1;
    }
    // if the roles are the same, we compare userIds
    if (a.value.userId < b.value.userId) {
      return -1;
    }
    if (a.value.userId > b.value.userId) {
      return 1;
    }
    return 0;
  }

  isUserAdminOrMember(project: Project | undefined) {
    const userId = this.authService.getUserId();

    // @ts-ignore
    const userInProject = project.users.find(user => user.userId == userId);

    if (userInProject?.role == 'ADMIN') {
      this.userAdmin = true;
    }

    if (userInProject?.role == 'MEMBER') {
      this.userMember = true;
    }
  }
}
