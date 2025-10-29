import {Employee} from '../Employee';
import {PageResponse} from './PageResponse';
import {AssignmentWithoutEmployee} from '../AssignmentWithoutEmployee';

export interface EmployeeAssignmentsResponse {
  employee: Employee;
  assignments: PageResponse<AssignmentWithoutEmployee>;
}
