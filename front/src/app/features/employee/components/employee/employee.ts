import {Component, inject, OnInit, signal, ViewChild, TemplateRef} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {NgbModal, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpErrorResponse} from '@angular/common/http';
import {EmployeeService} from '../../employee-service';
import {ActivatedRoute} from '@angular/router';
import {Employee as EmployeeModel} from '../../../../models/Employee';
import {PageResponse} from '../../../../models/response/PageResponse';
import {AssignmentWithoutEmployee} from '../../../../models/AssignmentWithoutEmployee';
import {AssignmentService} from '../../../../shared/services/assignment.service';
import {EmployeeAssignmentsResponse} from '../../../../models/response/EmployeeAssignmentsResponse';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';
import {TableConfig} from '../../../../shared/models/table-column';
import {ProjectService} from '../../../project/project.service';
import {Project} from '../../../../models/Project';
import {AssignmentRequest} from '../../../../models/request/AssignmentRequest';
import {AuthService} from '../../../../core/services/auth-service';

@Component({
  selector: 'app-employee',
  imports: [CommonModule, FormsModule, DataTableComponent, NgbModalModule],
  templateUrl: './employee.html',
  styleUrl: './employee.css',
})
export class Employee implements OnInit {
  private employeeService: EmployeeService = inject(EmployeeService);
  private assignmentService: AssignmentService = inject(AssignmentService);
  private projectService: ProjectService = inject(ProjectService);
  private tableConfigService: TableConfigService = inject(TableConfigService);
  private modalService: NgbModal = inject(NgbModal);
  private route: ActivatedRoute = inject(ActivatedRoute);

  employeeId = signal<number>(-1);
  employee = signal<EmployeeModel | null>(null);
  isLoading = signal(false);

  employeeAssignments =
    signal<PageResponse<AssignmentWithoutEmployee> | null>(null);
  isLoadingAssignments = signal(false);

  processingActions = signal<Set<number>>(new Set());
  selectedAssignment: AssignmentWithoutEmployee | null = null;

  // Assignment modal properties
  availableProjects = signal<Project[] | null>(null);
  isLoadingProjects = signal(false);
  selectedProjectId: number | null = null;
  assignmentHourRate: number | null = null;
  isAssigning = signal(false);

  // Vacation days editing properties
  newVacationDays: number | null = null;
  isUpdatingVacationDays = signal(false);

  @ViewChild('unassignModal') unassignModal!: TemplateRef<any>;
  @ViewChild('assignModal') assignModal!: TemplateRef<any>;
  @ViewChild('vacationDaysModal') vacationDaysModal!: TemplateRef<any>;

  assignmentTableConfig: TableConfig = this.tableConfigService.getAssignmentTableConfig(
    (projectId: number) => this.isProcessing(projectId)
  );

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        const employeeId = parseInt(id, 10);
        this.employeeId.set(employeeId);
        this.loadEmployee(employeeId);
        this.loadAssignments();
      }
    });
  }

  private loadEmployee(id: number) {
    this.isLoading.set(true);

    this.employeeService.get(id).subscribe({
      next: (employee) => {
        this.employee.set(employee);
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Failed to load employee:', error);
        this.isLoading.set(false);
        throw error;
      }
    });
  }

  private loadAssignments() {
    this.isLoadingAssignments.set(true);
    this.assignmentService.getForEmployee(this.employeeId()).subscribe({
      next: (response: EmployeeAssignmentsResponse) => {
        if (response === null) {
          this.employeeAssignments.set(null);
          this.isLoadingAssignments.set(false);
        } else {
          this.employeeAssignments.set(response.assignments);
          this.isLoadingAssignments.set(false);
        }
      },
      error: (error) => {
        console.error('Failed to loadAssignments:', error);
        this.isLoadingAssignments.set(false);
        throw error;
      }
    });
  }

  onTableAction(event: { action: string; row: AssignmentWithoutEmployee }) {
    if (event.action === 'unassign') {
      this.confirmUnassignment(event.row);
    }
  }

  confirmUnassignment(assignment: AssignmentWithoutEmployee) {
    this.selectedAssignment = assignment;

    const modalRef = this.modalService.open(this.unassignModal);

    modalRef.result.then((result) => {
      if (result === 'confirm') {
        this.unassignFromProject(assignment.project.id);
      }
    }, () => {
      // Modal dismissed
      this.selectedAssignment = null;
    });
  }

  unassignFromProject(projectId: number) {
    const employeeId = this.employeeId();

    const currentProcessing = this.processingActions();
    currentProcessing.add(projectId);
    this.processingActions.set(new Set(currentProcessing));

    this.assignmentService.unassign(employeeId, projectId).subscribe({
      next: () => {
        const newProcessing = this.processingActions();
        newProcessing.delete(projectId);
        this.processingActions.set(new Set(newProcessing));

        this.loadAssignments();
        this.selectedAssignment = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to unassign from project:', error);
        const newProcessing = this.processingActions();
        newProcessing.delete(projectId);
        this.processingActions.set(new Set(newProcessing));

        throw error;
      }
    });
  }

  isProcessing(projectId: number): boolean {
    return this.processingActions().has(projectId);
  }

  openAssignModal() {
    this.loadProjects();
    this.selectedProjectId = null;
    this.assignmentHourRate = null;

    const modalRef = this.modalService.open(this.assignModal, { size: 'lg' });

    modalRef.result.then((result) => {
      if (result === 'confirm' && this.selectedProjectId && this.assignmentHourRate) {
        this.assignToProject(this.selectedProjectId, this.assignmentHourRate);
      }
    }, () => {
      // Modal dismissed
      this.selectedProjectId = null;
      this.assignmentHourRate = null;
    });
  }

  private loadProjects() {
    this.isLoadingProjects.set(true);
    this.projectService.getAll().subscribe({
      next: (response) => {
        // Filter out projects the employee is already assigned to
        const currentAssignments = this.employeeAssignments();
        const assignedProjectIds = currentAssignments?.content
          ?.map(assignment => assignment.project.id) || [];

        const availableProjects = response.content.filter(
          project => !assignedProjectIds.includes(project.id)
        );

        this.availableProjects.set(availableProjects);
        this.isLoadingProjects.set(false);
      },
      error: (error) => {
        console.error('Failed to load projects:', error);
        this.isLoadingProjects.set(false);
        throw error;
      }
    });
  }

  assignToProject(projectId: number, hourRate: number) {
    this.isAssigning.set(true);

    const request: AssignmentRequest = {
      employeeId: this.employeeId(),
      projectId: projectId,
      hourRate: hourRate
    };

    this.assignmentService.assign(request).subscribe({
      next: () => {
        this.isAssigning.set(false);
        // Reload assignments to show the new assignment
        this.loadAssignments();
        // Clear form data
        this.selectedProjectId = null;
        this.assignmentHourRate = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to assign to project:', error);
        this.isAssigning.set(false);
        throw error;
      }
    });
  }

  openVacationDaysModal() {
    this.newVacationDays = this.employee()?.vacationDays || 0;

    const modalRef = this.modalService.open(this.vacationDaysModal);

    modalRef.result.then((result) => {
      if (result === 'confirm' && (this.newVacationDays || this.newVacationDays === 0)) {
        this.updateVacationDays(this.newVacationDays);
      }
    }, () => {
      // Modal dismissed
      this.newVacationDays = null;
    });
  }

  updateVacationDays(vacationDays: number) {
    this.isUpdatingVacationDays.set(true);

    this.employeeService.setVacationDays(this.employeeId(), vacationDays).subscribe({
      next: () => {
        this.isUpdatingVacationDays.set(false);
        // Update the employee signal with the new vacation days
        const currentEmployee = this.employee();
        if (currentEmployee) {
          this.employee.set({ ...currentEmployee, vacationDays });
        }
        // Clear form data
        this.newVacationDays = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to update vacation days:', error);
        this.isUpdatingVacationDays.set(false);
        throw error;
      }
    });
  }
}
