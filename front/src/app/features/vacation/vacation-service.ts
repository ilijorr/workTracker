import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {PageResponse} from '../../models/response/PageResponse';
import {Vacation} from '../../models/Vacation';

@Injectable({
  providedIn: 'root'
})
export class VacationService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = `${environment.apiUrl}/vacation`;

  getPending(
    page: number = 0, size: number = 100
  ): Observable<PageResponse<Vacation>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<PageResponse<Vacation>>(
      `${this.apiUrl}/pending`, {params: params}
    );
  }

  getAll(
    page: number = 0, size: number = 100
  ): Observable<PageResponse<Vacation>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<PageResponse<Vacation>>(
      `${this.apiUrl}`, {params: params}
    );
  }

  approve(id: number): Observable<Vacation> {
    return this.http.patch<Vacation>(`${this.apiUrl}/approve/${id}`, null)
  }

  decline(id: number): Observable<Vacation> {
    return this.http.patch<Vacation>(`${this.apiUrl}/decline/${id}`, null)
  }

}
