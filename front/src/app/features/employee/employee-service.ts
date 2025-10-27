import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {Employee} from '../../models/Employee';
import {PageResponse} from '../../models/response/PageResponse';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = environment.apiUrl + '/employee';

  getAll(page: number = 0, size: number = 100): Observable<PageResponse<Employee>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    console.log(params);
    return this.http.get<PageResponse<Employee>>(this.apiUrl, { params });
  }
}
