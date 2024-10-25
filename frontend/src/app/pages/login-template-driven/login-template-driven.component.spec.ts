import { ComponentFixture, TestBed } from '@angular/core/testing';
import {LoginTemplateDrivenComponent} from "./login-template-driven.component";
import {ReactiveFormsModule} from "@angular/forms";
import {ProjectService} from "../../services/project.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {of} from "rxjs";

describe('LoginTemplateDrivenComponent', () => {
  let component: LoginTemplateDrivenComponent;
  let fixture: ComponentFixture<LoginTemplateDrivenComponent>;

  let authServiceMock: any;

  beforeEach(async () => {
    authServiceMock = {
      login: jest.fn()
    };

    const activatedRouteMock = {
      snapshot: {
        queryParams: {
          'returnUrl': undefined
        }
      }
    };

    const routerMock = {
      navigateByUrl: jest.fn(),
    };

    await TestBed.configureTestingModule({
      declarations: [ LoginTemplateDrivenComponent ],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        { provide: Router, useValue: routerMock }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginTemplateDrivenComponent);
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

  it('should not call authService.login if form is invalid', () => {
    component.loginForm.controls['email'].setValue('');
    component.loginForm.controls['password'].setValue('');
    component.onSubmit();
    expect(authServiceMock.login).not.toHaveBeenCalled();
  });

  it('should call projectService.addTaskInProject with correct parameters when form is valid', () => {
    component.loginForm.controls['email'].setValue('a@b.c');
    component.loginForm.controls['password'].setValue('azerty');

    authServiceMock.login.mockReturnValue(of({}));

    component.onSubmit();

    expect(authServiceMock.login).toHaveBeenCalledWith(
      {
        email: 'a@b.c',
        password: 'azerty'
      }
    );
  });
});
