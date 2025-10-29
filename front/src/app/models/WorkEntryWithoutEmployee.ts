import {AssignmentWithoutEmployee} from './AssignmentWithoutEmployee';

export interface WorkEntryWithoutEmployee {
  assignment: AssignmentWithoutEmployee;
  yearMonth: string;
  hourCount: number;
}
