import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: 'navbar.css'
})
export class NavbarComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  logout() {
    this.authService.logout();
    void this.router.navigate(['/']);
  }

  isAdmin(): boolean {
    return this.authService.getUserRole()?.toLowerCase() === 'role_admin';
  }

  isEmployee(): boolean {
    return this.authService.getUserRole()?.toLowerCase() === 'role_employee';
  }
}
