import {Employee} from '../Employee';
import {PageResponse} from './PageResponse';
import {WorkEntryWithoutEmployee} from '../WorkEntryWithoutEmployee';

export interface EmployeeWorkEntriesResponse {
  employee: Employee;
  workEntries: PageResponse<WorkEntryWithoutEmployee>;
}
