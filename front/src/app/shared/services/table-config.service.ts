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
