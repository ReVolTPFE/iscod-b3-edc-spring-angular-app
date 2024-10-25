import {Component, Input} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {ProjectService} from "../../services/project.service";

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.scss']
})
export class UserRoleComponent {
  @Input() user!: Record<any, any>;
  @Input() userAdmin!: boolean;
  changeUserRoleInProjectForm: FormGroup;
  submitted = false;
  error = '';
  projectId = Number(this.route.snapshot.paramMap.get('projectId'));

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private projectService: ProjectService
  ) {
    this.changeUserRoleInProjectForm = this.formBuilder.group({
      role: ['', [Validators.required]],
    });
  }

  get f() { return this.changeUserRoleInProjectForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.changeUserRoleInProjectForm.invalid) {
      return;
    }

    const formRole = this.f['role'].value;

    const userToChangeRoleId = this.user['value'].userId;

    this.projectService.changeUserRole(this.projectId, userToChangeRoleId, formRole).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
