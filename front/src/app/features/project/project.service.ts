import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {PageResponse} from '../../models/response/PageResponse';
import {Project} from '../../models/Project';
import {CreateProjectRequest} from '../../models/request/CreateProjectRequest';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = `${environment.apiUrl}/project`;

  getAll(page: number = 0, size: number = 100): Observable<PageResponse<Project>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<PageResponse<Project>>(this.apiUrl, { params: params });
  }

  get(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/${id}`);
  }

  create(request: CreateProjectRequest): Observable<Project> {
    return this.http.post<Project>(this.apiUrl, request);
  }
}
