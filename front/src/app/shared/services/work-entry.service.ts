import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {PageResponse} from '../../models/response/PageResponse';
import {WorkEntry} from '../../models/WorkEntry';
import {EmployeeWorkEntriesResponse} from '../../models/response/EmployeeWorkEntriesResponse';
import {ProjectWorkEntriesResponse} from '../../models/response/ProjectWorkEntriesResponse';
import {CreateWorkEntryRequest} from '../../models/request/CreateWorkEntryRequest';
import {UpdateWorkEntryRequest} from '../../models/request/UpdateWorkEntryRequest';

@Injectable({
  providedIn: 'root'
})
export class WorkEntryService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = environment.apiUrl + '/work';

  getForEmployee(
    employeeId: number, yearMonth: string,
    page: number = 0, size: number = 100
  ): Observable<EmployeeWorkEntriesResponse> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('year-month', yearMonth);
    return this.http.get<EmployeeWorkEntriesResponse>(
      `${this.apiUrl}/employee/${employeeId}`, { params }
    )
  }

  getForProject(
    projectId: number, yearMonth: string,
    page: number = 0, size: number = 100
  ): Observable<ProjectWorkEntriesResponse> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('year-month', yearMonth);
    return this.http.get<ProjectWorkEntriesResponse>(
      `${this.apiUrl}/project/${projectId}`, { params }
    )
  }

  getMy(
    yearMonth: string,
    page: number = 0, size: number = 100
  ): Observable<EmployeeWorkEntriesResponse> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('year-month', yearMonth);
    return this.http.get<EmployeeWorkEntriesResponse>(
      `${this.apiUrl}/me`, { params }
    )
  }

  getForYearMonth(
    yearMonth: string, page: number = 0, size: number = 100
  ): Observable<PageResponse<WorkEntry>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<PageResponse<WorkEntry>>(
      `${this.apiUrl}/year-month/${yearMonth}`, { params }
    );
  }

  create(
    request: CreateWorkEntryRequest
  ): Observable<WorkEntry> {
    return this.http.post<WorkEntry>(this.apiUrl, request);
  }

  update(
    request: UpdateWorkEntryRequest
  ): Observable<WorkEntry> {
    return this.http.patch<WorkEntry>(this.apiUrl, request);
  }

  delete(
    projectId: number, yearMonth: string
  ): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/project/${projectId}/year-month/${yearMonth}`
    );
  }
}
