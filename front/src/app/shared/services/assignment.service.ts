import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {EmployeeAssignmentsResponse} from '../../models/response/EmployeeAssignmentsResponse';
import {ProjectAssignmentsResponse} from '../../models/response/ProjectAssignmentsResponse';
import {AssignmentRequest} from '../../models/request/AssignmentRequest';
import {Assignment} from '../../models/Assignment';

@Injectable({
  providedIn: 'root'
})
export class AssignmentService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = environment.apiUrl + '/assignment';

  getForEmployee(
    id: number, page: number = 0, size: number = 100): Observable<EmployeeAssignmentsResponse> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<EmployeeAssignmentsResponse>(`${this.apiUrl}/employee/${id}`, {params: params});
  }

  getMy(
    page: number = 0, size: number = 100): Observable<EmployeeAssignmentsResponse> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<EmployeeAssignmentsResponse>(`${this.apiUrl}/me`, {params: params});
  }

  getForProject(
    id: number, page: number = 0, size: number = 100): Observable<ProjectAssignmentsResponse> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<ProjectAssignmentsResponse>(`${this.apiUrl}/project/${id}`, {params: params});
  }

  assign(request: AssignmentRequest): Observable<Assignment> {
    return this.http.post<Assignment>(`${this.apiUrl}`, request);
  }

  unassign(employeeId: number, projectId: number): Observable<boolean> {
    return this.http.patch<boolean>(`${this.apiUrl}/${employeeId}/${projectId}`, {});
  }

}
