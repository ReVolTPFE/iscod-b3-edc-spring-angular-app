import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import {ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {of} from "rxjs";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  let authServiceMock: any;

  beforeEach(async () => {
    authServiceMock = {
      register: jest.fn()
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
      declarations: [ RegisterComponent ],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        { provide: Router, useValue: routerMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
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

  it('should not call authService.register if form is invalid', () => {
    component.registerForm.controls['username'].setValue('');
    component.registerForm.controls['email'].setValue('');
    component.registerForm.controls['password'].setValue('');
    component.onSubmit();
    expect(authServiceMock.register).not.toHaveBeenCalled();
  });

  it('should call projectService.register with correct parameters when form is valid', () => {
    component.registerForm.controls['username'].setValue('user1');
    component.registerForm.controls['email'].setValue('a@b.c');
    component.registerForm.controls['password'].setValue('azerty');

    authServiceMock.register.mockReturnValue(of({}));

    component.onSubmit();

    expect(authServiceMock.register).toHaveBeenCalledWith(
      {
        username: 'user1',
        email: 'a@b.c',
        password: 'azerty'
      }
    );
  });
});
