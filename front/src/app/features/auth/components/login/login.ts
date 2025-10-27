import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../../../core/services/auth-service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {CommonModule} from '@angular/common';

// TODO: guestGuard for login component

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  private formBuilder: FormBuilder = inject(FormBuilder);
  loginForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(5)]],
  })
  isLoading = false;
  errorMessage: string = "";

  submit(): void {
    this.isLoading = true;
    this.errorMessage = "";

    if (this.loginForm.invalid) {
      console.log("invalid login attempt");
      console.log(this.loginForm.value);
      return;
    }

    const request: LoginRequest = { ...this.loginForm.value } as LoginRequest;

    this.authService.login(request).subscribe({
      next: (response: LoginResponse) => {
        this.isLoading = false;
        console.log("Login successful");

        // void this.router.navigate(['/dashboard']);
      },
      error: (error: HttpErrorResponse) => {
        this.isLoading = false;
        console.error(error.message);
        if (error.status === 401 || error.status === 403) {
          this.errorMessage = "Username or password is incorrect";
        } else if (error.status === 0) {
          this.errorMessage = "Unable to connect to the server";
        } else {
          this.errorMessage = "Unexpected error occurred";
        }
      }
    })

  }
}
