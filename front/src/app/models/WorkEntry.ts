import { Assignment } from './Assignment';

export interface WorkEntry {
  assignment: Assignment;
  yearMonth: string;
  hourCount: number;
}