import {Component, EventEmitter, Output} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.scss']
})
export class AddTaskComponent {
  addTaskForm: FormGroup;
  submitted = false;
  error = '';
  projectId = Number(this.route.snapshot.paramMap.get('projectId'));
  @Output() taskCreated = new EventEmitter<void>();

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private formBuilder: FormBuilder,
  ) {
    this.addTaskForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      status: ['', [Validators.required]],
      priority: ['', [Validators.required]],
      dueDate: ['', [Validators.required]]
    });
  }

  get f() { return this.addTaskForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.addTaskForm.invalid) {
      return;
    }

    const formData = {
      name: this.f['name'].value,
      description: this.f['description'].value,
      status: this.f['status'].value,
      priority: this.f['priority'].value,
      dueDate: this.f['dueDate'].value,
      endedAt: null
    };

    this.projectService.addTaskInProject(this.projectId, formData).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {
        this.error = error.error?.message || 'An error occurred during task creation';
      }
    });
  }
}
