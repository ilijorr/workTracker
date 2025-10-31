import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeService } from '../../employee-service';
import { TableConfigService } from '../../../../shared/services/table-config.service';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table';
import { PageResponse, createEmptyPageResponse } from '../../../../models/response/PageResponse';
import { Employee } from '../../../../models/Employee';
import { TableConfig } from '../../../../shared/models/table-column';
import {CreateEmployeeRequest} from '../../../../models/request/CreateEmployeeRequest';

@Component({
  selector: 'app-employee-manage',
  imports: [CommonModule, ReactiveFormsModule, DataTableComponent],
  templateUrl: './employee-manage.html',
  styleUrl: './employee-manage.css'
})
export class EmployeeManageComponent implements OnInit {
  private employeeService = inject(EmployeeService);
  private tableConfigService = inject(TableConfigService);
  private formBuilder = inject(FormBuilder);

  employeeData = signal<PageResponse<Employee>>(createEmptyPageResponse<Employee>());
  isLoadingEmployees = signal(false);
  tableConfig: TableConfig = this.tableConfigService.getEmployeeTableConfig();

  createEmployeeForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(5)]]
  });
  isCreatingEmployee = signal(false);
  createEmployeeMessage = signal('');

  vacationDaysForm: FormGroup = this.formBuilder.group({
    employeeId: ['', [Validators.required]],
    vacationDays: ['', [Validators.required, Validators.min(0)]]
  });
  isUpdatingVacation = signal(false);
  vacationUpdateMessage = signal('');

  ngOnInit() {
    this.loadEmployees(0);
  }

  loadEmployees(page: number) {
    this.isLoadingEmployees.set(true);

    this.employeeService.getAll(page, 100).subscribe({
      next: (pageResponse: PageResponse<Employee>) => {
        this.employeeData.set(pageResponse);
        this.isLoadingEmployees.set(false);
      },
      error: (error) => {
        console.error('Failed to load employees:', error);
        this.isLoadingEmployees.set(false);
      }
    });
  }

  onSort(event: any) {
    console.log('Sort event:', event);
  }

  createEmployee() {
    if (this.createEmployeeForm.invalid) return;

    this.isCreatingEmployee.set(true);
    this.createEmployeeMessage.set('');

    const formValue = this.createEmployeeForm.value;

    const request: CreateEmployeeRequest = {...this.createEmployeeForm.value} as CreateEmployeeRequest;
    this.employeeService.create(request).subscribe({
      next: (employee) => {
        this.createEmployeeMessage.set('Employee created successfully!');
        this.createEmployeeForm.reset();
        this.loadEmployees(0); // Refresh the table
        this.isCreatingEmployee.set(false);
      },
      error: (error) => {
        this.createEmployeeMessage.set('Failed to create employee. Please try again.');
        console.error('Failed to create employee:', error);
        this.isCreatingEmployee.set(false);
      }
    });
  }

  updateVacationDays() {
    if (this.vacationDaysForm.invalid) return;

    this.isUpdatingVacation.set(true);
    this.vacationUpdateMessage.set('');

    const formValue = this.vacationDaysForm.value;

    this.employeeService.setVacationDays(formValue.employeeId, formValue.vacationDays).subscribe({
      next: () => {
        this.vacationUpdateMessage.set('Vacation days updated successfully!');
        this.vacationDaysForm.reset();
        this.loadEmployees(0); // Refresh the table
        this.isUpdatingVacation.set(false);
      },
      error: (error) => {
        this.vacationUpdateMessage.set('Failed to update vacation days. Please try again.');
        console.error('Failed to update vacation days:', error);
        this.isUpdatingVacation.set(false);
      }
    });
  }
}
