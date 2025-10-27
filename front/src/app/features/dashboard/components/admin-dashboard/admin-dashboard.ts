import {Component, inject, OnInit, signal} from '@angular/core';
import {EmployeeService} from '../../../employee/employee-service';
import {Router} from '@angular/router';
import {TableConfig} from '../../../../shared/models/table-column';
import {PageResponse} from '../../../../models/response/PageResponse';
import {Employee} from '../../../../models/Employee';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';

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
  tableConfigService: TableConfigService = inject(TableConfigService);
  router: Router = inject(Router);

  employeeData = signal<PageResponse<Employee>>({
    content: [],
    totalElements: 0,
    totalPages: 0,
    size: 100,
    number: 0,
    first: true,
    last: true
  });

  isLoading = signal(false);

  employeeTableConfig: TableConfig = this.tableConfigService.getEmployeeTableConfig();

  loadEmployees(page: number) {
    this.isLoading.set(true);

    this.employeeService.getAll(page, 100).subscribe({
      next: (pageResponse: PageResponse<Employee>) => {
        this.employeeData.set(pageResponse);
        this.isLoading.set(false);
      },
      error: error => {
      console.log(error);
      this.isLoading.set(false);
      }
    })
  }

  ngOnInit() {
    this.loadEmployees(0);
  }

  onSort(event: any): void {
    console.log('onSort executed for event ', event);
  }

}
