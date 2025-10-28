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
        // redirect to default error page
        break;
      case 401:
        void this.router.navigate(['/login']);
        break;
      case 403:
        void this.router.navigate(['/dashboard']);
        break;
    }
  }

}
