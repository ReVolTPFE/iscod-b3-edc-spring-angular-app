import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRoleComponent } from './user-role.component';
import {ReactiveFormsModule} from "@angular/forms";
import {ProjectService} from "../../services/project.service";
import {ActivatedRoute} from "@angular/router";
import {of} from "rxjs";

describe('UserRoleComponent', () => {
  let component: UserRoleComponent;
  let fixture: ComponentFixture<UserRoleComponent>;

  let projectServiceMock: any;

  beforeEach(async () => {
    projectServiceMock = {
      changeUserRole: jest.fn(),
    };

    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: (key: string) => {
            if (key === 'projectId') {
              return '1';
            }
            return null;
          }
        }
      }
    };

    await TestBed.configureTestingModule({
      declarations: [ UserRoleComponent ],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserRoleComponent);
    component = fixture.componentInstance;

    component.user = { value: { userId: 1 } };

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should mark form as submitted on submit', () => {
    component.onSubmit();
    expect(component.submitted).toBe(true);
  });

  it('should not call projectService.changeUserRole if form is invalid', () => {
    component.changeUserRoleInProjectForm.controls['role'].setValue('');
    component.onSubmit();
    expect(projectServiceMock.changeUserRole).not.toHaveBeenCalled();
  });

  it('should call projectService.changeUserRole with correct parameters when form is valid', () => {
    const role = 'MEMBER';

    component.changeUserRoleInProjectForm.controls['role'].setValue(role);

    projectServiceMock.changeUserRole.mockReturnValue(of({}));

    component.onSubmit();


    expect(projectServiceMock.changeUserRole).toHaveBeenCalledWith(
      component.projectId,
      component.user['value'].userId,
      role
    );
  });
});
