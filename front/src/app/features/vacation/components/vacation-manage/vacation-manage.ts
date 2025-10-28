import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import { VacationService } from '../../vacation-service';
import { TableConfigService } from '../../../../shared/services/table-config.service';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table';
import { PageResponse, createEmptyPageResponse } from '../../../../models/response/PageResponse';
import { Vacation } from '../../../../models/Vacation';
import { TableConfig } from '../../../../shared/models/table-column';

@Component({
  selector: 'app-vacation-manage',
  imports: [CommonModule, NgbAccordionModule, DataTableComponent],
  templateUrl: './vacation-manage.html',
  styleUrl: './vacation-manage.css'
})
export class VacationManageComponent implements OnInit {
  private vacationService = inject(VacationService);
  private tableConfigService = inject(TableConfigService);

  pendingRequestsData = signal<PageResponse<Vacation>>(createEmptyPageResponse<Vacation>());
  isLoadingPending = signal(false);

  allRequestsData = signal<PageResponse<Vacation>>(createEmptyPageResponse<Vacation>());
  isLoadingAll = signal(false);

  processingActions = signal<Set<number>>(new Set());

  pendingTableConfig: TableConfig = this.tableConfigService.getVacationPendingTableConfig(
    (id: number) => this.isProcessing(id)
  );
  allRequestsTableConfig: TableConfig = this.tableConfigService.getVacationTableConfig();

  ngOnInit() {
    this.loadPendingRequests(0);
    this.loadAllRequests(0);
  }

  loadPendingRequests(page: number) {
    this.isLoadingPending.set(true);

    this.vacationService.getPending(page, 100).subscribe({
      next: (pageResponse: PageResponse<Vacation>) => {
        this.pendingRequestsData.set(pageResponse);
        this.isLoadingPending.set(false);
      },
      error: (error) => {
        console.error('Failed to load pending vacation requests:', error);
        this.isLoadingPending.set(false);
      }
    });
  }

  loadAllRequests(page: number = 0) {
    this.isLoadingAll.set(true);

    this.vacationService.getAll(page, 100).subscribe({
      next: (pageResponse: PageResponse<Vacation>) => {
        this.allRequestsData.set(pageResponse);
        this.isLoadingAll.set(false);
      },
      error: (error) => {
        console.error('Failed to load all vacation requests:', error);
        this.isLoadingAll.set(false);
      }
    });
  }

  onTableAction(event: { action: string; row: Vacation }) {
    if (event.action === 'approve') {
      this.approveRequest(event.row.id);
    } else if (event.action === 'decline') {
      this.declineRequest(event.row.id);
    }
  }

  approveRequest(vacationId: number) {
    const currentProcessing = this.processingActions();
    currentProcessing.add(vacationId);
    this.processingActions.set(new Set(currentProcessing));

    this.vacationService.approve(vacationId).subscribe({
      next: (updatedVacation) => {
        // Remove from processing
        const newProcessing = this.processingActions();
        newProcessing.delete(vacationId);
        this.processingActions.set(new Set(newProcessing));

        // Refresh both tables
        this.loadPendingRequests(0);
        this.loadAllRequests(0);
      },
      error: (error) => {
        console.error('Failed to approve vacation request:', error);
        const newProcessing = this.processingActions();
        newProcessing.delete(vacationId);
        this.processingActions.set(new Set(newProcessing));
      }
    });
  }

  declineRequest(vacationId: number) {
    const currentProcessing = this.processingActions();
    currentProcessing.add(vacationId);
    this.processingActions.set(new Set(currentProcessing));

    this.vacationService.decline(vacationId).subscribe({
      next: (updatedVacation) => {
        // Remove from processing
        const newProcessing = this.processingActions();
        newProcessing.delete(vacationId);
        this.processingActions.set(new Set(newProcessing));

        // Refresh both tables
        this.loadPendingRequests(0);
        this.loadAllRequests(0);
      },
      error: (error) => {
        console.error('Failed to decline vacation request:', error);
        const newProcessing = this.processingActions();
        newProcessing.delete(vacationId);
        this.processingActions.set(new Set(newProcessing));
      }
    });
  }

  isProcessing(vacationId: number): boolean {
    return this.processingActions().has(vacationId);
  }

  onSort(event: any) {
    console.log('Sort event:', event);
  }
}
