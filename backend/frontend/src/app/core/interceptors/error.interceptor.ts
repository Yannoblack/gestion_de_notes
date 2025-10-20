import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        // Unauthorized - redirect to login
        authService.logout();
        router.navigate(['/login']);
      } else if (error.status === 403) {
        // Forbidden - show error message
        console.error('Access denied:', error.message);
      } else if (error.status === 404) {
        // Not found
        console.error('Resource not found:', error.message);
      } else if (error.status >= 500) {
        // Server error
        console.error('Server error:', error.message);
      }

      return throwError(() => error);
    })
  );
};
