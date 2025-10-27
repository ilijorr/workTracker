import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/components/login/login').then(m => m.Login)
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/dashboard/components/role-based-dashboard/role-based-dashboard').then(m => m.RoleBasedDashboard)
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
