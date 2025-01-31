import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IntegrationService } from '../../services/integration.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { SignupRequest } from '../../models/signup-request';


@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  signupForm!: FormGroup;
  msg: string | undefined;

  constructor(
    private integrationService: IntegrationService,
    private storage: LocalStorageService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  request: SignupRequest = new SignupRequest();

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      address: ['', Validators.required],
      mobileno: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]], // Mobile number validation
      age: ['', [Validators.required, Validators.min(18)]], // Age validation (at least 18)
    });
  }

  onSubmit(): void {
    if (this.signupForm.valid) {
      console.log(this.signupForm.value);

      this.integrationService.doRegister(this.signupForm.value).subscribe(() => {

      });
      // Form verilerini işleyin, örneğin backend'e gönderin.
      // Başarılı işlem sonrası mesaj gösterilebilir.
      this.msg = 'Registration successful!';
      this.router.navigate(['/login']);
    } else {
      console.error('Form is invalid');
      // Hata mesajı gösterilebilir
      this.msg = 'Form is invalid. Please fill out all fields correctly.';
    }
  }
}
