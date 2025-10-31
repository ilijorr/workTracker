export interface ActionButton {
  label: string;
  action: string; // event name to emit
  class?: string; // CSS classes for styling
  disabled?: (row: any) => boolean; // function to determine if button is disabled
  loading?: (row: any) => boolean; // function to determine if button is loading
  hidden?: (row: any) => boolean; // function to determine if button is hidden
  icon?: string; // optional icon class
}

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
  actions?: {
    buttons: ActionButton[];
    width?: string; // CSS width for actions column
  };
}