import {Component, Input} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {AuthService} from "../../services/auth.service";

interface User {
  userId: number;
  username: string;
  email: string;
  role: string;
}

@Component({
  selector: 'app-add-user-to-task',
  templateUrl: './add-user-to-task.component.html',
  styleUrls: ['./add-user-to-task.component.scss']
})
export class AddUserToTaskComponent {
  assignUserToTheTaskForm: FormGroup;
  submittedAssign = false;
  projectId = Number(this.route.snapshot.paramMap.get('projectId'));
  taskId = Number(this.route.snapshot.paramMap.get('taskId'));
  @Input() usersNotInTask: User[] = [];

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
  ) {
    this.assignUserToTheTaskForm = this.formBuilder.group({
      userId: ['', [Validators.required]],
    });
  }

  get fAssignUser() { return this.assignUserToTheTaskForm.controls; }

  onSubmitAssignUser() {
    this.submittedAssign = true;

    if (this.assignUserToTheTaskForm.invalid) {
      return;
    }

    const formUserId = this.fAssignUser['userId'].value;

    this.projectService.assignUserInTask(parseInt(formUserId), this.projectId, this.taskId).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
