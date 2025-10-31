import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth-service';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // First check if user is authenticated
  if (!authService.isAuthenticated()) {
    return router.createUrlTree(['/login']);
  }

  // Then check if user is admin
  if (authService.getUserRole()?.toLowerCase() === 'role_admin') {
    return true;
  }

  // If authenticated but not admin, redirect to dashboard
  return router.createUrlTree(['/dashboard']);
};
