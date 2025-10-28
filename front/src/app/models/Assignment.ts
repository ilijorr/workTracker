import { Employee } from './Employee';
import { Project } from './Project';

export interface Assignment {
  employee: Employee;
  project: Project;
  hourRate: number;
}