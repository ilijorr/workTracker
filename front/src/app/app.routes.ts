import { Routes } from '@angular/router';
import {guestGuard} from './core/guards/guest-guard';
import {authGuard} from './core/guards/auth-guard';
import {inject} from '@angular/core';
import {AuthService} from './core/services/auth-service';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/components/login/login').then(m => m.Login),
    canActivate: [
      guestGuard,
    ],
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/dashboard/components/role-based-dashboard/role-based-dashboard').then(m => m.RoleBasedDashboard),
    canActivate: [
      authGuard,
    ],
  },
  {
    path: '',
    redirectTo: () => {
      const authService = inject(AuthService)
      return authService.isAuthenticated() ? 'dashboard' : 'login';
    },
    pathMatch: 'full'
  }
];
