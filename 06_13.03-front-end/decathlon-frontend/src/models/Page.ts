export interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    number: number; // praegune lehekülg (0-indekseeritud)
    size: number;
}
