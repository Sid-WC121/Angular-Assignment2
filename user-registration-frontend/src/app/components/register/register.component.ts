import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  template: `
    <div>
      <h2>Register</h2>
      <form (ngSubmit)="onRegister()">
        <input 
          [(ngModel)]="user.username" 
          name="username" 
          placeholder="Username" 
          required
        >
        <input 
          [(ngModel)]="user.password" 
          name="password" 
          type="password" 
          placeholder="Password" 
          required
        >
        <button type="submit">Register</button>
      </form>
      <p *ngIf="errorMessage" class="error">{{errorMessage}}</p>
    </div>
  `
})
export class RegisterComponent {
  user = { username: '', password: '' };
  errorMessage = '';

  constructor(
    private authService: AuthService, 
    private router: Router
  ) {}

  onRegister() {
    this.authService.register(this.user).subscribe(
      () => {
        this.router.navigate(['/login']);
      },
      error => {
        this.errorMessage = error.error;
      }
    );
  }
}