import {Project} from '../Project';
import {PageResponse} from './PageResponse';
import {AssignmentWithoutProject} from '../AssignmentWithoutProject';

export interface ProjectAssignmentsResponse {
  project: Project;
  assignments: PageResponse<AssignmentWithoutProject>;
}
