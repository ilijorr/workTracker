import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {environment} from '../../../environments/environment';
import {jwtDecode} from 'jwt-decode';

interface JwtPayload {
  exp?: number;
  role?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl = environment.apiUrl + '/public/auth';

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request)
      .pipe(
        tap({
          next: (response: LoginResponse) => {
            this.setSession(response);
          },
        }),
      );
  }

  private setSession(response: LoginResponse) {
    const decodedPayload = jwtDecode(response.jwt) as JwtPayload;

    localStorage.setItem('token', response.jwt);
    localStorage.setItem('exp', decodedPayload.exp?.toString() || '');
    localStorage.setItem('role', decodedPayload.role || '');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('exp');
    localStorage.removeItem('role');
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    const exp = localStorage.getItem('exp');

    if (!token || !exp) {
      return false;
    }

    const now = Math.floor(Date.now() / 1000);
    return parseInt(exp) > now;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRole(): string | null {
    return localStorage.getItem('role');
  }
}
