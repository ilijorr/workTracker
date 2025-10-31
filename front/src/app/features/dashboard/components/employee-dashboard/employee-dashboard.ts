import {Component, inject, OnInit, signal, ViewChild, TemplateRef} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {NgbModal, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpErrorResponse} from '@angular/common/http';
import {WorkEntryService} from '../../../../shared/services/work-entry.service';
import {AssignmentService} from '../../../../shared/services/assignment.service';
import {WorkEntry} from '../../../../models/WorkEntry';
import {WorkEntryWithoutEmployee} from '../../../../models/WorkEntryWithoutEmployee';
import {PageResponse} from '../../../../models/response/PageResponse';
import {AssignmentWithoutEmployee} from '../../../../models/AssignmentWithoutEmployee';
import {EmployeeWorkEntriesResponse} from '../../../../models/response/EmployeeWorkEntriesResponse';
import {EmployeeAssignmentsResponse} from '../../../../models/response/EmployeeAssignmentsResponse';
import {CreateWorkEntryRequest} from '../../../../models/request/CreateWorkEntryRequest';
import {UpdateWorkEntryRequest} from '../../../../models/request/UpdateWorkEntryRequest';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';
import {TableConfig} from '../../../../shared/models/table-column';

@Component({
  selector: 'app-employee-dashboard',
  imports: [CommonModule, FormsModule, DataTableComponent, NgbModalModule],
  templateUrl: './employee-dashboard.html',
  styleUrl: './employee-dashboard.css',
})
export class EmployeeDashboard implements OnInit {
  private workEntryService: WorkEntryService = inject(WorkEntryService);
  private assignmentService: AssignmentService = inject(AssignmentService);
  private tableConfigService: TableConfigService = inject(TableConfigService);
  private modalService: NgbModal = inject(NgbModal);

  // Work entries table
  workEntries = signal<PageResponse<WorkEntryWithoutEmployee> | null>(null);
  isLoadingWorkEntries = signal(false);
  selectedYearMonth = signal<string>(this.getCurrentYearMonth());

  // My assignments for form
  myAssignments = signal<PageResponse<AssignmentWithoutEmployee> | null>(null);
  isLoadingAssignments = signal(false);

  // Form data
  selectedProjectId: number | null = null;
  formYear: number = new Date().getFullYear();
  formMonth: number = new Date().getMonth() + 1;
  formHours: number | null = null;
  isSubmitting = signal(false);
  isEditing = false;
  editingEntry: WorkEntryWithoutEmployee | null = null;

  // Delete confirmation
  deletingEntry: WorkEntryWithoutEmployee | null = null;

  // Month names for display
  monthNames: string[] = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];

  @ViewChild('deleteModal') deleteModal!: TemplateRef<any>;

  workEntryTableConfig: TableConfig = this.tableConfigService.getEmployeeWorkEntryTableConfig();

  ngOnInit() {
    this.loadMyAssignments();
    this.loadWorkEntries();
  }

  private getCurrentYearMonth(): string {
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    return `${year}-${month}`;
  }

  private loadMyAssignments() {
    this.isLoadingAssignments.set(true);
    this.assignmentService.getMy().subscribe({
      next: (response: EmployeeAssignmentsResponse) => {
        this.myAssignments.set(response.assignments);
        this.isLoadingAssignments.set(false);
      },
      error: (error) => {
        console.error('Failed to load assignments:', error);
        this.isLoadingAssignments.set(false);
        throw error;
      }
    });
  }

  private loadWorkEntries() {
    this.isLoadingWorkEntries.set(true);
    this.workEntryService.getMy(this.selectedYearMonth()).subscribe({
      next: (response: EmployeeWorkEntriesResponse) => {
        this.workEntries.set(response.workEntries);
        this.isLoadingWorkEntries.set(false);
      },
      error: (error) => {
        console.error('Failed to load work entries:', error);
        this.isLoadingWorkEntries.set(false);
        throw error;
      }
    });
  }

  onYearMonthChange() {
    const year = this.formYear;
    const month = this.formMonth.toString().padStart(2, '0');
    this.selectedYearMonth.set(`${year}-${month}`);
    this.loadWorkEntries();
  }

  onTableAction(event: { action: string; row: WorkEntryWithoutEmployee }) {
    if (event.action === 'edit') {
      this.editWorkEntry(event.row);
    } else if (event.action === 'delete') {
      this.confirmDelete(event.row);
    }
  }

  editWorkEntry(entry: WorkEntryWithoutEmployee) {
    this.isEditing = true;
    this.editingEntry = entry;
    this.selectedProjectId = entry.assignment.project.id;

    // Parse year-month
    const [year, month] = entry.yearMonth.split('-');
    this.formYear = parseInt(year);
    this.formMonth = parseInt(month);
    this.formHours = entry.hourCount;
  }

  confirmDelete(entry: WorkEntryWithoutEmployee) {
    this.deletingEntry = entry;
    this.modalService.open(this.deleteModal);
  }

  deleteWorkEntry() {
    if (!this.deletingEntry) return;

    this.workEntryService.delete(
      this.deletingEntry.assignment.project.id,
      this.deletingEntry.yearMonth
    ).subscribe({
      next: () => {
        this.loadWorkEntries();
        this.deletingEntry = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to delete work entry:', error);
        throw error;
      }
    });
  }

  submitForm() {
    if (!this.selectedProjectId || !this.formHours) return;

    const yearMonth = `${this.formYear}-${this.formMonth.toString().padStart(2, '0')}`;
    this.isSubmitting.set(true);

    if (this.isEditing && this.editingEntry) {
      // Update existing entry
      const request: UpdateWorkEntryRequest = {
        projectId: this.selectedProjectId,
        yearMonth: yearMonth,
        hourCount: this.formHours
      };

      this.workEntryService.update(request).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.loadWorkEntries();
          this.resetForm();
        },
        error: (error: HttpErrorResponse) => {
          console.error('Failed to update work entry:', error);
          this.isSubmitting.set(false);
          throw error;
        }
      });
    } else {
      // Create new entry
      const request: CreateWorkEntryRequest = {
        projectId: this.selectedProjectId,
        yearMonth: yearMonth,
        hourCount: this.formHours
      };

      this.workEntryService.create(request).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.loadWorkEntries();
          this.resetForm();
        },
        error: (error: HttpErrorResponse) => {
          console.error('Failed to create work entry:', error);
          this.isSubmitting.set(false);
          throw error;
        }
      });
    }
  }

  resetForm() {
    this.selectedProjectId = null;
    this.formYear = new Date().getFullYear();
    this.formMonth = new Date().getMonth() + 1;
    this.formHours = null;
    this.isEditing = false;
    this.editingEntry = null;
  }

  isFormValid(): boolean {
    return !!(
      this.selectedProjectId &&
      this.formHours &&
      this.formHours > 0 &&
      this.formHours <= 720 &&
      this.formHours % 1 === 0 && // Must be integer
      this.formYear &&
      this.formMonth >= 1 &&
      this.formMonth <= 12
    );
  }
}
