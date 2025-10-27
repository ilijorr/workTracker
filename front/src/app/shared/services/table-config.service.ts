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

  getProjectTableConfig(): TableConfig {
    return {
      columns: [
        {
          key: 'name',
          label: 'Project Name',
          type: 'link',
          linkConfig: {
            routePath: '/project',
            idField: 'id',
            displayField: 'name'
          },
          sortable: true
        },
        { key: 'description', label: 'Description' },
        { key: 'status', label: 'Status', sortable: true },
        { key: 'startDate', label: 'Start Date', type: 'date', sortable: true },
        { key: 'endDate', label: 'End Date', type: 'date', sortable: true },
        { key: 'budget', label: 'Budget', type: 'number' }
      ],
      excludeFields: ['internalNotes'],
      striped: true,
      hover: true,
      bordered: false
    };
  }

  getCustomConfig(baseConfigName: string, overrides: Partial<TableConfig>): TableConfig {
    let baseConfig: TableConfig;

    switch (baseConfigName) {
      case 'employee':
        baseConfig = this.getEmployeeTableConfig();
        break;
      case 'project':
        baseConfig = this.getProjectTableConfig();
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
