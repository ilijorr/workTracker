import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth-service';

export const employeeGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // First check if user is authenticated
  if (!authService.isAuthenticated()) {
    return router.createUrlTree(['/login']);
  }

  // Then check if user is admin
  if (authService.getUserRole()?.toLowerCase() === 'role_employee') {
    return true;
  }

  // If authenticated but not employee, redirect to dashboard
  return router.createUrlTree(['/dashboard']);
};
