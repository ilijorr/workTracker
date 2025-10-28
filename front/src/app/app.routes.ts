import { Routes } from '@angular/router';
import {guestGuard} from './core/guards/guest-guard';
import {authGuard} from './core/guards/auth-guard';
import {inject} from '@angular/core';
import {AuthService} from './core/services/auth-service';
import {adminGuard} from './core/guards/admin-guard';

// TODO: feature specific routing files

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
    path: 'employee/:id',
    loadComponent: () =>
      import('./features/employee/components/employee/employee').then(m => m.Employee),
    canActivate: [
      adminGuard
    ],
  },
  {
    path: 'project/:id',
    loadComponent: () =>
      import('./features/project/components/project/project').then(m => m.Project),
    canActivate: [
      authGuard,
    ]
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
