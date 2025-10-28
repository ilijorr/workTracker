import {Component, inject, OnInit, signal} from '@angular/core';
import {EmployeeService} from '../../../employee/employee-service';
import {Router} from '@angular/router';
import {TableConfig} from '../../../../shared/models/table-column';
import {PageResponse, createEmptyPageResponse} from '../../../../models/response/PageResponse';
import {Employee} from '../../../../models/Employee';
import {Project} from '../../../../models/Project';
import {WorkEntry} from '../../../../models/WorkEntry';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';
import {ProjectService} from '../../../project/project.service';
import {WorkEntryService} from '../../../../shared/services/work-entry.service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [
    DataTableComponent
  ],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {
  employeeService: EmployeeService = inject(EmployeeService);
  projectService: ProjectService = inject(ProjectService);
  workEntryService: WorkEntryService = inject(WorkEntryService);
  tableConfigService: TableConfigService = inject(TableConfigService);
  router: Router = inject(Router);

  employeeData = signal<PageResponse<Employee>>(
    createEmptyPageResponse<Employee>()
  );
  projectData = signal<PageResponse<Project>>(
    createEmptyPageResponse<Project>()
  );
  workEntryData = signal<PageResponse<WorkEntry>>(
    createEmptyPageResponse<WorkEntry>()
  );

  isLoadingEmployees = signal(false);
  isLoadingProjects = signal(false);
  isLoadingWorkEntries = signal(false);

  employeeTableConfig: TableConfig = this.tableConfigService.getEmployeeTableConfig();
  projectTableConfig: TableConfig = this.tableConfigService.getProjectTableConfig();
  workEntryTableConfig: TableConfig = this.tableConfigService.getWorkEntryTableConfig();

  loadEmployees(page: number) {
    this.isLoadingEmployees.set(true);

    this.employeeService.getAll(page, 100).subscribe({
      next: (pageResponse: PageResponse<Employee>) => {
        this.employeeData.set(pageResponse);
      },
      error: error => {
        console.log(error);
      }
    })
    this.isLoadingEmployees.set(false);
  }

  loadProjects(page: number) {
    this.isLoadingProjects.set(true);

    this.projectService.getAll(page, 100).subscribe({
      next: (pageResponse: PageResponse<Project>) => {
        this.projectData.set(pageResponse);
      },
      error: error => {
        console.log(error);
      }
    })
    this.isLoadingProjects.set(false);
  }

  loadWorkEntries(page: number) {
    this.isLoadingWorkEntries.set(true);

    // Get current year-month (format: "2024-03")
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const currentYearMonth = `${year}-${month}`;

    this.workEntryService.getForYearMonth(currentYearMonth, page, 100).subscribe({
      next: (pageResponse: PageResponse<WorkEntry>) => {
        console.log(pageResponse);
        this.workEntryData.set(pageResponse);
      },
      error: error => {
        console.log(error);
      }
    });
    this.isLoadingWorkEntries.set(false);
  }

  ngOnInit() {
    this.loadEmployees(0);
    this.loadProjects(0);
    this.loadWorkEntries(0);
  }

  onSort(event: any): void {
    console.log('onSort executed for event ', event);
  }

}
