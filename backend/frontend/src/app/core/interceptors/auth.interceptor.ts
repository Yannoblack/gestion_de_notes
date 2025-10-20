import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  console.log('[AuthInterceptor] Request URL:', req.url);
  console.log('[AuthInterceptor] Token:', token ? 'Present (length: ' + token.length + ')' : 'NOT FOUND');

  if (token) {
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    console.log('[AuthInterceptor] ✅ Token added to headers');
    return next(authReq);
  }

  console.log('[AuthInterceptor] ❌ No token, request sent without Authorization header');
  return next(req);
};
