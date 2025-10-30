import { Routes } from '@angular/router';
import {guestGuard} from './core/guards/guest-guard';
import {authGuard} from './core/guards/auth-guard';
import {inject} from '@angular/core';
import {AuthService} from './core/services/auth-service';
import {adminGuard} from './core/guards/admin-guard';
import {employeeGuard} from './core/guards/employee-guard';

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
    path: 'employee/manage',
    loadComponent: () =>
      import('./features/employee/components/employee-manage/employee-manage').then(m => m.EmployeeManageComponent),
    canActivate: [
      adminGuard
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
    path: 'vacation/manage',
    loadComponent: () =>
      import('./features/vacation/components/vacation-manage/vacation-manage').then(m => m.VacationManageComponent),
    canActivate: [
      adminGuard
    ],
  },
  {
    path: 'vacation',
    loadComponent: () =>
      import('./features/vacation/components/vacation-employee/vacation-employee').then(m => m.VacationEmployeeComponent),
    canActivate: [
      employeeGuard
    ],
  },
  {
    path: 'error',
    loadComponent: () =>
      import('./core/components/error-page/error-page').then(m => m.ErrorPageComponent),
  },
  {
    path: 'project/:id',
    loadComponent: () =>
      import('./features/project/components/role-based-project/role-based-project').then(m => m.RoleBasedProject),
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
