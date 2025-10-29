import {Component, inject, OnInit, signal, ViewChild, TemplateRef} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {NgbModal, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpErrorResponse} from '@angular/common/http';
import {ProjectService} from '../../project.service';
import {ActivatedRoute} from '@angular/router';
import {Project as ProjectModel} from '../../../../models/Project';
import {PageResponse} from '../../../../models/response/PageResponse';
import {AssignmentWithoutProject} from '../../../../models/AssignmentWithoutProject';
import {AssignmentService} from '../../../../shared/services/assignment.service';
import {ProjectAssignmentsResponse} from '../../../../models/response/ProjectAssignmentsResponse';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';
import {TableConfig} from '../../../../shared/models/table-column';
import {EmployeeService} from '../../../employee/employee-service';
import {Employee} from '../../../../models/Employee';
import {AssignmentRequest} from '../../../../models/request/AssignmentRequest';

@Component({
  selector: 'app-project',
  imports: [CommonModule, FormsModule, DataTableComponent, NgbModalModule],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project implements OnInit {
  private projectService: ProjectService = inject(ProjectService);
  private assignmentService: AssignmentService = inject(AssignmentService);
  private employeeService: EmployeeService = inject(EmployeeService);
  private route: ActivatedRoute = inject(ActivatedRoute);
  private tableConfigService: TableConfigService = inject(TableConfigService);
  private modalService: NgbModal = inject(NgbModal);

  projectId = signal<number>(-1);
  project = signal<ProjectModel | null>(null);
  isLoading = signal(false);

  projectAssignments = signal<PageResponse<AssignmentWithoutProject> | null>(null);
  isLoadingAssignments = signal(false);

  processingActions = signal<Set<number>>(new Set());
  selectedAssignment: AssignmentWithoutProject | null = null;

  // Assignment modal properties
  availableEmployees = signal<Employee[] | null>(null);
  isLoadingEmployees = signal(false);
  selectedEmployeeId: number | null = null;
  assignmentHourRate: number | null = null;
  isAssigning = signal(false);

  @ViewChild('unassignModal') unassignModal!: TemplateRef<any>;
  @ViewChild('assignModal') assignModal!: TemplateRef<any>;

  assignmentTableConfig: TableConfig = this.tableConfigService.getProjectAssignmentTableConfig(
    (employeeId: number) => this.isProcessing(employeeId)
  );

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        const projectId = parseInt(id, 10);
        this.projectId.set(projectId);
        this.loadProject(projectId);
        this.loadAssignments();
      }
    });
  }

  private loadProject(id: number) {
    this.isLoading.set(true);

    this.projectService.get(id).subscribe({
      next: (project) => {
        this.project.set(project);
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Failed to load project:', error);
        this.isLoading.set(false);
        throw error;
      }
    });
  }

  private loadAssignments() {
    this.isLoadingAssignments.set(true);
    this.assignmentService.getForProject(this.projectId()).subscribe({
      next: (response: ProjectAssignmentsResponse) => {
        if (response === null) {
          this.projectAssignments.set(null);
          this.isLoadingAssignments.set(false);
        } else {
          this.projectAssignments.set(response.assignments);
          this.isLoadingAssignments.set(false);
        }
      },
      error: (error) => {
        console.error('Failed to load assignments:', error);
        this.isLoadingAssignments.set(false);
        throw error;
      }
    });
  }

  onTableAction(event: { action: string; row: AssignmentWithoutProject }) {
    if (event.action === 'unassign') {
      this.confirmUnassignment(event.row);
    }
  }

  confirmUnassignment(assignment: AssignmentWithoutProject) {
    this.selectedAssignment = assignment;

    const modalRef = this.modalService.open(this.unassignModal);

    modalRef.result.then((result) => {
      if (result === 'confirm') {
        this.unassignFromProject(assignment.employee.id);
      }
    }, () => {
      // Modal dismissed
      this.selectedAssignment = null;
    });
  }

  unassignFromProject(employeeId: number) {
    const projectId = this.projectId();

    const currentProcessing = this.processingActions();
    currentProcessing.add(employeeId);
    this.processingActions.set(new Set(currentProcessing));

    this.assignmentService.unassign(employeeId, projectId).subscribe({
      next: () => {
        const newProcessing = this.processingActions();
        newProcessing.delete(employeeId);
        this.processingActions.set(new Set(newProcessing));

        this.loadAssignments();
        this.selectedAssignment = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to unassign from project:', error);
        const newProcessing = this.processingActions();
        newProcessing.delete(employeeId);
        this.processingActions.set(new Set(newProcessing));

        throw error;
      }
    });
  }

  isProcessing(employeeId: number): boolean {
    return this.processingActions().has(employeeId);
  }

  openAssignModal() {
    this.loadEmployees();
    this.selectedEmployeeId = null;
    this.assignmentHourRate = null;

    const modalRef = this.modalService.open(this.assignModal, { size: 'lg' });

    modalRef.result.then((result) => {
      if (result === 'confirm' && this.selectedEmployeeId && this.assignmentHourRate) {
        this.assignToEmployee(this.selectedEmployeeId, this.assignmentHourRate);
      }
    }, () => {
      // Modal dismissed
      this.selectedEmployeeId = null;
      this.assignmentHourRate = null;
    });
  }

  private loadEmployees() {
    this.isLoadingEmployees.set(true);
    this.employeeService.getAll().subscribe({
      next: (response) => {
        // Filter out employees already assigned to this project
        const currentAssignments = this.projectAssignments();
        const assignedEmployeeIds = currentAssignments?.content
          ?.map(assignment => assignment.employee.id) || [];

        const availableEmployees = response.content.filter(
          employee => !assignedEmployeeIds.includes(employee.id)
        );

        this.availableEmployees.set(availableEmployees);
        this.isLoadingEmployees.set(false);
      },
      error: (error) => {
        console.error('Failed to load employees:', error);
        this.isLoadingEmployees.set(false);
        throw error;
      }
    });
  }

  assignToEmployee(employeeId: number, hourRate: number) {
    this.isAssigning.set(true);

    const request: AssignmentRequest = {
      employeeId: employeeId,
      projectId: this.projectId(),
      hourRate: hourRate
    };

    this.assignmentService.assign(request).subscribe({
      next: () => {
        this.isAssigning.set(false);
        // Reload assignments to show the new assignment
        this.loadAssignments();
        // Clear form data
        this.selectedEmployeeId = null;
        this.assignmentHourRate = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to assign employee:', error);
        this.isAssigning.set(false);
        throw error;
      }
    });
  }
}
