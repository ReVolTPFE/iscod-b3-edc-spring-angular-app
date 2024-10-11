import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";

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

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {
    this.getTask();

    this.editTaskForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      status: ['', [Validators.required]],
      priority: ['', [Validators.required]],
      dueDate: ['', [Validators.required]],
      endedAt: ['', []]
    });
  }

  get f() { return this.editTaskForm.controls; }

  onSubmit() {
    this.submitted = true;

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
}