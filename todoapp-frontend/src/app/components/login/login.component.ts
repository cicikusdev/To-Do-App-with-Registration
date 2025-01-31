import { Component, inject } from '@angular/core';
import { IntegrationService } from '../../services/integration.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequest } from '../../models/login-request';
import { Router, RouterLink } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage.service';

@Component({
  selector: 'app-login',
  standalone: false,

  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private integration: IntegrationService,  private storage : LocalStorageService) {}

  userForm: FormGroup = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required])
  });

  router = inject(Router);
  request: LoginRequest = new LoginRequest();

  doLogin() {

    // Remove any previous auth token
    this.storage.remove('auth-key');

    const formValue = this.userForm.value;

    if (formValue.email === '' || formValue.password === '') {
      alert('Wrong Credentials');
      return;
    }

    // Prepare the login request
    this.request.email = formValue.email;
    this.request.password = formValue.password;


    // Make the login request
    this.integration.doLogin(this.request).subscribe({
      next: (res) => {
        console.log("Received Response: " + res.token);

        // Store the received token
        this.storage.set('auth-key', res.token);
        this.router.navigateByUrl("/todo");
        // Once login is successful, navigate to the "Todo" component
        // this.integration.todo().subscribe({
        //   next: (todoRes) => {
        //     console.log("Todo response: " + todoRes.response);
        //
        //     // Redirect to the Todo page
        //
        //   },
        //   error: (err) => {
        //     console.log("Todo error received: " + err);
        //     this.storage.remove('auth-key');
        //   }
        // });
      },
      error: (err) => {
        console.log("Error Received Response: " + err);
        this.storage.remove('auth-key');
      }
    });
  }
}
