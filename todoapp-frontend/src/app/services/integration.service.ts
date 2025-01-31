import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginResponse} from '../models/login-response';
import {LoginRequest} from '../models/login-request';
import {Observable} from 'rxjs';
import {SignupRequest} from '../models/signup-request';
import {SignupResponse} from '../models/signup-response';

const API_URL = "http://localhost:8080/api";
@Injectable({
  providedIn: 'root'
})
export class IntegrationService {

  constructor(private http: HttpClient) { }

  // Giriş işlemi
  doLogin(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${API_URL}/doLogin`, request);
    // auth/login endpoint kullanılarak login yapılır
  }

  // Kayıt işlemi
  doRegister(request: SignupRequest): Observable<SignupResponse> {
    console.log("sdfgh", request);
    return this.http.post<SignupResponse>(`${API_URL}/doRegister`, request); // auth/register endpoint'i
  }
}

