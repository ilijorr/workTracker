import {Component, inject, OnInit, signal} from '@angular/core';
import {EmployeeService} from '../../employee-service';
import {ActivatedRoute} from '@angular/router';
import {Employee as EmployeeModel} from '../../../../models/Employee';

@Component({
  selector: 'app-employee',
  imports: [],
  templateUrl: './employee.html',
  styleUrl: './employee.css',
})
export class Employee implements OnInit {
  private employeeService: EmployeeService = inject(EmployeeService);
  private route: ActivatedRoute = inject(ActivatedRoute);

  employeeId = signal<number>(-1);
  employee = signal<EmployeeModel | null>(null);
  isLoading = signal(false);

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        const employeeId = parseInt(id, 10);
        this.employeeId.set(employeeId);
        this.loadEmployee(employeeId);
      }
    });
  }

  private loadEmployee(id: number) {
    this.isLoading.set(true);

    this.employeeService.get(id).subscribe({
      next: (employee) => {
        this.employee.set(employee);
      },
      error: (error) => {
        console.error('Failed to load employee:', error);
      }
    });
    this.isLoading.set(false);
  }

}
