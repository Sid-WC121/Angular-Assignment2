import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  template: `
    <div>
      <h2>Login</h2>
      <form (ngSubmit)="onLogin()">
        <input 
          [(ngModel)]="credentials.username" 
          name="username" 
          placeholder="Username" 
          required
        >
        <input 
          [(ngModel)]="credentials.password" 
          name="password" 
          type="password" 
          placeholder="Password" 
          required
        >
        <button type="submit">Login</button>
      </form>
      <p *ngIf="errorMessage" class="error">{{errorMessage}}</p>
    </div>
  `
})
export class LoginComponent {
  credentials = { username: '', password: '' };
  errorMessage = '';

  constructor(
    private authService: AuthService, 
    private router: Router
  ) {}

  onLogin() {
    this.authService.login(this.credentials).subscribe(
      () => {
        this.router.navigate(['/dashboard']);
      },
      error => {
        this.errorMessage = error.error;
      }
    );
  }
}