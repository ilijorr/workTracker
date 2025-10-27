export interface TableColumn {
  key: string;
  label: string;
  sortable?: boolean;
  type?: 'text' | 'number' | 'date' | 'boolean' | 'link';
  linkConfig?: {
    routePath: string;     // e.g., '/employee'
    idField: string;       // e.g., 'id' - field to use for routing
    displayField?: string; // e.g., 'username' - field to display as link text
  };
}

export interface TableConfig {
  columns: TableColumn[];
  excludeFields?: string[];
  sortable?: boolean;
  striped?: boolean;
  bordered?: boolean;
  hover?: boolean;
}