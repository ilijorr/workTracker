import {ErrorHandler, inject} from '@angular/core';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

export class GlobalErrorHandler implements ErrorHandler {
  private router: Router = inject(Router);

  handleError(error: any): void {
    if (error instanceof HttpErrorResponse) {
      this.handleHttpError(error);
    } else {
      // more error handling logic
      console.error(error);
    }
  }

  private handleHttpError(error: HttpErrorResponse): void {
    switch (error.status) {
      case 0:
        console.error('Network error or server unavailable:', error);
        void this.router.navigate(['/error']);
        break;
      case 401:
        console.error('Unauthorized access:', error);
        void this.router.navigate(['/login']);
        break;
      case 403:
        console.error('Forbidden access:', error);
        void this.router.navigate(['/dashboard']);
        break;
      default:
        console.error('HTTP error:', error);
        break;
    }
  }

}
