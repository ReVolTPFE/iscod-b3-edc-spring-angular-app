import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {AuthService} from "../../services/auth.service";
import {forkJoin, Observable} from "rxjs";

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

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent {
  editTaskForm: FormGroup;
  submitted = false;
  error = '';
  task: Task | null | undefined;
  projectId = Number(this.route.snapshot.paramMap.get('projectId'));
  taskId = Number(this.route.snapshot.paramMap.get('taskId'));
  @Output() taskCreated = new EventEmitter<void>();
  userAdmin = false;
  userMember = false;
  project?: Project;
  usersNotInTask: User[] = [];

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
  ) {
    this.loadData(this.projectId);

    this.editTaskForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      status: ['', [Validators.required]],
      priority: ['', [Validators.required]],
      dueDate: ['', [Validators.required]],
      endedAt: ['', []]
    });
  }

  //to prevent loading of users before projects
  loadData(projectId: number) {
    forkJoin({
      project: this.projectService.getProject(projectId),
      task: this.projectService.getTask(projectId, this.taskId)
    }).subscribe({
      next: (response) => {
        this.project = response.project;
        this.task = response.task;
        this.isUserAdminOrMember(this.project);

        this.usersNotInTask = this.filterUsersNotInTask();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  get f() { // @ts-ignore
    return this.editTaskForm.controls; }

  onSubmit() {
    this.submitted = true;

    // @ts-ignore
    if (this.editTaskForm.invalid) {
      return;
    }

    const formData = {
      name: this.f['name'].value,
      description: this.f['description'].value,
      status: this.f['status'].value,
      priority: this.f['priority'].value,
      dueDate: this.f['dueDate'].value,
      endedAt: this.f['endedAt'].value
    };

    this.projectService.editTaskInProject(this.projectId, this.taskId, formData).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {
        this.error = error.error?.message || 'An error occurred during task edition';
      }
    });
  }

  getTask() {
    this.projectService.getTask(this.projectId, this.taskId).subscribe({
      next: (response) => {
        this.task = response;

        // @ts-ignore
        this.editTaskForm.patchValue({
          name: this.getLastTaskHistory(response)?.name,
          description: this.getLastTaskHistory(response)?.description,
          status: this.getLastTaskHistory(response)?.status,
          priority: this.getLastTaskHistory(response)?.priority,
          dueDate: this.getLastTaskHistory(response)?.dueDate,
          endedAt: this.getLastTaskHistory(response)?.endedAt || ''
        });
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getLastTaskHistory(task: Task) {
    return task.taskHistories.length > 0 ? task.taskHistories[task.taskHistories.length - 1] : null;
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

  filterUsersNotInTask() {
    // @ts-ignore
    return this.project.users.filter(projectUser => {
      // @ts-ignore
      return !this.task.users.some(taskUser => taskUser.id === projectUser.userId);
    });
  }
}
