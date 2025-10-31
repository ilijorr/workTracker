import {Project} from '../Project';
import {PageResponse} from './PageResponse';
import {WorkEntryWithoutProject} from '../WorkEntryWithoutProject';

export interface ProjectWorkEntriesResponse {
  project: Project;
  workEntries: PageResponse<WorkEntryWithoutProject>;
}
