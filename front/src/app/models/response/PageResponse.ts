export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export function createEmptyPageResponse<T>(size: number = 10): PageResponse<T> {
  return {
    content: [],
    totalElements: 0,
    totalPages: 0,
    size,
    number: 0,
    first: true,
    last: true
  };
}
