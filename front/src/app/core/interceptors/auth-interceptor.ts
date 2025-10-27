import { HttpInterceptorFn } from '@angular/common/http';
import {AuthService} from '../services/auth-service';
import {inject} from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService: AuthService = inject(AuthService);

  if (!authService.isAuthenticated()) {
    return next(req);
  }

  const requestWithToken = req.clone({
    headers: req.headers.set(
      "Authorization", "Bearer " + authService.getToken()
    )
  })
  return next(requestWithToken);
};
