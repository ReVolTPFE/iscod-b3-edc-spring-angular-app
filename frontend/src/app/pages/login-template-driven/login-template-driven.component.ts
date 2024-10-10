import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-login-template-driven',
  templateUrl: './login-template-driven.component.html',
  styleUrls: ['./login-template-driven.component.scss']
})
export class LoginTemplateDrivenComponent {
  loginForm: FormGroup;
  submitted = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    console.log(this.loginForm)
    if (this.loginForm.invalid) {
      return;
    }

    const formData = {
      email: this.f['email'].value,
      password: this.f['password'].value
    };

    this.authService.login(formData).subscribe({
      next: () => {
        const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/project';
        this.router.navigate([returnUrl]);
      },
      error: (error) => {
        this.error = error.error?.message || 'An error occurred during login';
        console.error('Login error:', error);
      }
    });
  }
}
