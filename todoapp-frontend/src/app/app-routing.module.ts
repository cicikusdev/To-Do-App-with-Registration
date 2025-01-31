import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TodoComponent } from './todo/todo.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { authGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'todo', component: TodoComponent, canActivate: [authGuard] }, // authGuard ekli
  { path: 'login', component: LoginComponent }, // Login sayfası
  { path: 'register', component: RegisterComponent }, // Register sayfası
  // { path: '**', redirectTo: 'login' }, // Geçersiz rotalarda login sayfasına yönlendirme
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
