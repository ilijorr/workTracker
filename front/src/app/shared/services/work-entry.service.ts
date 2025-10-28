import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {PageResponse} from '../../models/response/PageResponse';
import {WorkEntry} from '../../models/WorkEntry';

@Injectable({
  providedIn: 'root'
})
export class WorkEntryService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = environment.apiUrl + '/work';

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
}
