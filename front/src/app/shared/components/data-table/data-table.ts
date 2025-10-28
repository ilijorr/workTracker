import { Component, input, output, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PageResponse } from '../../../models/response/PageResponse';
import { TableColumn, TableConfig, ActionButton } from '../../models/table-column';

@Component({
  selector: 'app-data-table',
  imports: [CommonModule, RouterModule],
  templateUrl: './data-table.html',
  styleUrl: './data-table.css'
})
export class DataTableComponent<T> {
  data = input.required<PageResponse<T>>();
  config = input.required<TableConfig>();

  pageChange = output<number>();
  sort = output<{ field: string; direction: 'asc' | 'desc' }>();
  action = output<{ action: string; row: T }>();

  displayedColumns = computed(() => {
    const excludeFields = this.config().excludeFields || [];
    return this.config().columns.filter(col => !excludeFields.includes(col.key));
  });


  onPageChange(page: number) {
    this.pageChange.emit(page);
  }

  onSort(column: TableColumn) {
    if (column.sortable) {
      this.sort.emit({ field: column.key, direction: 'asc' });
    }
  }

  formatValue(value: any, type: string): string {
    if (value === null || value === undefined) return '';

    switch (type) {
      case 'date':
        return new Date(value).toLocaleDateString();
      case 'boolean':
        return value ? 'Yes' : 'No';
      case 'number':
        return value.toString();
      default:
        return value.toString();
    }
  }

  getLinkPath(item: any, column: TableColumn): string {
    if (!column.linkConfig) return '';
    const id = this.getNestedValue(item, column.linkConfig.idField);
    return `${column.linkConfig.routePath}/${id}`;
  }

  getLinkText(item: any, column: TableColumn): string {
    if (!column.linkConfig) return '';
    const displayField = column.linkConfig.displayField || column.key;
    return this.getNestedValue(item, displayField) || '';
  }

  getNestedValue(obj: any, path: string): any {
    return path.split('.').reduce((current, key) => {
      return current && current[key] !== undefined ? current[key] : null;
    }, obj);
  }

  getCellValue(item: any, column: TableColumn): any {
    return this.getNestedValue(item, column.key);
  }

  onAction(actionName: string, row: T) {
    this.action.emit({ action: actionName, row });
  }

  isButtonDisabled(button: ActionButton, row: T): boolean {
    return button.disabled ? button.disabled(row) : false;
  }

  isButtonLoading(button: ActionButton, row: T): boolean {
    return button.loading ? button.loading(row) : false;
  }
}