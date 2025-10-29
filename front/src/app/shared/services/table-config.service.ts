import { Injectable } from '@angular/core';
import { TableConfig } from '../models/table-column';

@Injectable({
  providedIn: 'root'
})
export class TableConfigService {

  getEmployeeTableConfig(): TableConfig {
    return {
      columns: [
        {
          key: 'username', label: 'Username', type: 'link',
          linkConfig: {
            routePath: '/employee',
            idField: 'id',
            displayField: 'username',
          }
        },
        { key: 'vacationDays', label: 'Vacation Days' },
      ],
      striped: true,
      hover: true,
    };
  }

  getWorkEntryTableConfig(): TableConfig {
    return {
      columns: [
        {
          key: 'assignment.employee.username',
          label: 'Employee',
          type: 'link',
          linkConfig: {
            routePath: '/employee',
            idField: 'assignment.employee.id',
            displayField: 'assignment.employee.username',
          }
        },
        {
          key: 'assignment.project.name',
          label: 'Project Name',
          type: 'link',
          linkConfig: {
            routePath: '/project',
            idField: 'assignment.project.id',
            displayField: 'assignment.project.name',
          }
        },
        {
          key: 'yearMonth',
          label: 'Month',
          type: 'text',
        },
        {
          key: 'hourCount',
          label: 'Hours',
          type: 'number',
        },
        {
          key: 'assignment.hourRate',
          label: 'Rate',
          type: 'number',
        }
      ],
      striped: true,
      hover: true,
    }
  }

  getProjectTableConfig(): TableConfig {
    return {
      columns: [
        {
          key: 'name',
          label: 'Name',
          type: 'link',
          linkConfig: {
            routePath: '/project',
            idField: 'id',
            displayField: 'name'
          },
        },
        { key: 'description', label: 'Description', type: 'text' },
      ],
      striped: true,
      hover: true,
      bordered: false
    };
  }

  getVacationTableConfig(): TableConfig {
    return {
      columns: [
        {
          key: 'employee.username',
          label: 'Employee',
          type: 'link',
          linkConfig: {
            routePath: '/employee',
            idField: 'employee.id',
            displayField: 'employee.username',
          }
        },
        { key: 'startDate', label: 'Start Date', type: 'date' },
        { key: 'endDate', label: 'End Date', type: 'date' },
        { key: 'status', label: 'Status', type: 'text' },
      ],
      striped: true,
      hover: true,
    }
  }

  getVacationPendingTableConfig(
    isProcessing: (id: number) => boolean
  ): TableConfig {
    return {
      columns: [
        {
          key: 'employee.username',
          label: 'Employee',
          type: 'link',
          linkConfig: {
            routePath: '/employee',
            idField: 'employee.id',
            displayField: 'employee.username',
          }
        },
        { key: 'startDate', label: 'Start Date', type: 'date' },
        { key: 'endDate', label: 'End Date', type: 'date' },
      ],
      actions: {
        buttons: [
          {
            label: 'Approve',
            action: 'approve',
            class: 'btn-success',
            disabled: (row: any) => isProcessing(row.id),
            loading: (row: any) => isProcessing(row.id)
          },
          {
            label: 'Decline',
            action: 'decline',
            class: 'btn-danger',
            disabled: (row: any) => isProcessing(row.id),
            loading: (row: any) => isProcessing(row.id)
          }
        ],
        width: '200px'
      },
      striped: true,
      hover: true,
    }
  }

  getAssignmentTableConfig(
    isProcessing: (projectId: number) => boolean
  ): TableConfig {
    return {
      columns: [
        {
          key: 'project.name',
          label: 'Project Name',
          type: 'link',
          linkConfig: {
            routePath: '/project',
            idField: 'project.id',
            displayField: 'project.name',
          }
        },
        { key: 'project.description', label: 'Description', type: 'text' },
        { key: 'hourRate', label: 'Hour Rate', type: 'number' },
      ],
      actions: {
        buttons: [
          {
            label: 'Unassign',
            action: 'unassign',
            class: 'btn-danger',
            disabled: (row: any) => isProcessing(row.project.id),
            loading: (row: any) => isProcessing(row.project.id)
          }
        ],
        width: '120px'
      },
      striped: true,
      hover: true,
    }
  }

  getProjectAssignmentTableConfig(
    isProcessing: (employeeId: number) => boolean
  ): TableConfig {
    return {
      columns: [
        {
          key: 'employee.username',
          label: 'Employee',
          type: 'link',
          linkConfig: {
            routePath: '/employee',
            idField: 'employee.id',
            displayField: 'employee.username',
          }
        },
        { key: 'hourRate', label: 'Hour Rate', type: 'number' },
      ],
      actions: {
        buttons: [
          {
            label: 'Unassign',
            action: 'unassign',
            class: 'btn-danger',
            disabled: (row: any) => isProcessing(row.employee.id),
            loading: (row: any) => isProcessing(row.employee.id)
          }
        ],
        width: '120px'
      },
      striped: true,
      hover: true,
    }
  }

  getCustomConfig(baseConfigName: string, overrides: Partial<TableConfig>): TableConfig {
    let baseConfig: TableConfig;

    switch (baseConfigName) {
      case 'employee':
        baseConfig = this.getEmployeeTableConfig();
        break;
      case 'workEntry':
        baseConfig = this.getWorkEntryTableConfig();
        break;
      case 'project':
        baseConfig = this.getProjectTableConfig();
        break;
      case 'vacation':
        baseConfig = this.getVacationTableConfig();
        break;
      default:
        throw new Error(`Unknown table config: ${baseConfigName}`);
    }

    return {
      ...baseConfig,
      ...overrides,
      columns: overrides.columns || baseConfig.columns,
      excludeFields: overrides.excludeFields || baseConfig.excludeFields
    };
  }
}
