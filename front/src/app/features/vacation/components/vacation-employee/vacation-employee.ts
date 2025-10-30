import {Component, inject, OnInit, signal, ViewChild, TemplateRef} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbModal, NgbModalModule, NgbDatepicker, NgbDateStruct, NgbDate, NgbDatepickerModule, NgbCalendar, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {HttpErrorResponse} from '@angular/common/http';
import {VacationService} from '../../vacation-service';
import {Vacation} from '../../../../models/Vacation';
import {PageResponse} from '../../../../models/response/PageResponse';
import {CreateVacationRequest} from '../../../../models/request/CreateVacationRequest';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';
import {TableConfig} from '../../../../shared/models/table-column';

@Component({
  selector: 'app-vacation-employee',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, DataTableComponent, NgbModalModule, NgbDatepickerModule],
  templateUrl: './vacation-employee.html',
  styleUrl: './vacation-employee.css',
})
export class VacationEmployeeComponent implements OnInit {
  private vacationService: VacationService = inject(VacationService);
  private tableConfigService: TableConfigService = inject(TableConfigService);
  private modalService: NgbModal = inject(NgbModal);
  private formBuilder: FormBuilder = inject(FormBuilder);
  private calendar: NgbCalendar = inject(NgbCalendar);
  formatter: NgbDateParserFormatter = inject(NgbDateParserFormatter);

  // Vacation requests table
  vacationRequests = signal<PageResponse<Vacation> | null>(null);
  isLoadingVacations = signal(false);

  // Form data
  vacationForm: FormGroup;
  isSubmitting = signal(false);
  fromDate: NgbDate | null = null;
  toDate: NgbDate | null = null;
  hoveredDate: NgbDate | null = null;

  // Delete confirmation
  deletingVacation: Vacation | null = null;

  @ViewChild('deleteModal') deleteModal!: TemplateRef<any>;

  vacationTableConfig: TableConfig;

  constructor() {
    this.vacationForm = this.formBuilder.group({
      fromDate: [null, Validators.required],
      toDate: [null, Validators.required]
    });

    // Create table config with conditional delete buttons
    this.vacationTableConfig = {
      columns: [
        { key: 'startDate', label: 'Start Date', type: 'date' },
        { key: 'endDate', label: 'End Date', type: 'date' },
        { key: 'status', label: 'Status', type: 'text' },
      ],
      actions: {
        buttons: [
          {
            label: 'Delete',
            action: 'delete',
            class: 'btn-danger',
            hidden: (row: Vacation) => row.status !== 'PENDING'
          }
        ],
        width: '120px'
      },
      striped: true,
      hover: true,
    };
  }

  ngOnInit() {
    this.loadMyVacationRequests();
  }

  private loadMyVacationRequests() {
    this.isLoadingVacations.set(true);
    this.vacationService.getMy().subscribe({
      next: (response: PageResponse<Vacation>) => {
        this.vacationRequests.set(response);
        this.isLoadingVacations.set(false);
      },
      error: (error) => {
        console.error('Failed to load vacation requests:', error);
        this.isLoadingVacations.set(false);
        throw error;
      }
    });
  }

  onTableAction(event: { action: string; row: Vacation }) {
    if (event.action === 'delete') {
      this.confirmDelete(event.row);
    }
  }

  confirmDelete(vacation: Vacation) {
    this.deletingVacation = vacation;
    this.modalService.open(this.deleteModal);
  }

  deleteVacationRequest() {
    if (!this.deletingVacation) return;

    this.vacationService.delete(this.deletingVacation.id).subscribe({
      next: () => {
        this.loadMyVacationRequests();
        this.deletingVacation = null;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to delete vacation request:', error);
        throw error;
      }
    });
  }

  submitForm() {
    if (this.vacationForm.invalid || !this.fromDate || !this.toDate) return;

    const request: CreateVacationRequest = {
      startDate: this.formatDate(this.fromDate),
      endDate: this.formatDate(this.toDate)
    };

    this.isSubmitting.set(true);

    this.vacationService.create(request).subscribe({
      next: () => {
        this.isSubmitting.set(false);
        this.loadMyVacationRequests();
        this.resetForm();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to create vacation request:', error);
        this.isSubmitting.set(false);
        throw error;
      }
    });
  }

  resetForm() {
    this.vacationForm.reset();
    this.fromDate = null;
    this.toDate = null;
  }

  formatDate(date: NgbDate): string {
    return `${date.year}-${date.month.toString().padStart(2, '0')}-${date.day.toString().padStart(2, '0')}`;
  }

  isFormValid(): boolean {
    return !!(this.fromDate && this.toDate);
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
    this.vacationForm.patchValue({ fromDate: this.fromDate, toDate: this.toDate });
  }

  isHovered(date: NgbDate): boolean {
    return !!(this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate));
  }

  isInside(date: NgbDate): boolean {
    return !!(this.toDate && this.fromDate && date.after(this.fromDate) && date.before(this.toDate));
  }

  isRange(date: NgbDate): boolean {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) || this.isHovered(date);
  }

  validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
    const parsed = this.formatter.parse(input);
    return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
  }
}
