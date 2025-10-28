import {Employee} from './Employee';

export interface Vacation {
  id: number;
  employee: Employee;
  status: 'PENDING' | 'APPROVED' | 'DECLINED';
  startDate: string;
  endDate: string;
}
