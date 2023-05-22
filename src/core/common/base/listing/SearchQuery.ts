import { Query } from 'asimov-cqbus/dist/requests/Query'
import { LocalDateTime } from '@/core/common/base/time/LocalDateTime'
import { Nullable } from '@/core/common/base/lang/Nullable'

export abstract class SearchQuery<TItem, TFilters extends object = any> extends Query<PaginatedResult<TItem>> {
    constructor(
        public filters: TFilters,
        public pagination?: Pagination,
        public sorting?: Sorting,
    ) {
        super()
    }
}

export interface Pagination {
    page: number
    pageSize?: number
    lastCreationDate?: Nullable<LocalDateTime>
}

export interface Sorting {
    property: string
    direction: SortDirections
}

export enum SortDirections {
    Asc = 'Asc',
    Desc = 'Desc'
}

export interface PaginatedResult<T> {
    items: T[]
    totalItems: number
    lastCreationDate: Nullable<LocalDateTime>
}

export interface SearchActionConstructor<TItem, TFilters extends object = any> {
    new (filters: TFilters, pagination?: Pagination, sorting?: Sorting): SearchQuery<TItem, TFilters>
}
