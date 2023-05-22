import { Pagination, SearchQuery, Sorting } from '@/core/common/base/listing/SearchQuery'
import { QueryStringBuilder } from '@/core/common/infrastructure/http/QueryStringBuilder'
import { JsonDateSerializer } from '@/core/common/base/time/JsonDateSerializer'

export class SearchQueryStringBuilder {
    constructor(private dateSerializer: JsonDateSerializer) {}

    build(searchQuery: SearchQuery<any>) {
        const queryBuilder = new QueryStringBuilder()
        this.addPagination(searchQuery.pagination, queryBuilder)
        this.addSorting(searchQuery.sorting, queryBuilder)
        this.addFilters(searchQuery.filters, queryBuilder)
        return queryBuilder.build()
    }

    private addPagination(pagination: Pagination, queryBuilder: QueryStringBuilder) {
        if (!pagination) return
        queryBuilder.add('page', pagination.page)
        if (pagination.lastCreationDate) {
            queryBuilder.add('lastCreationDate', this.dateSerializer.toISO8601(pagination.lastCreationDate))
        }
    }

    private addSorting(sorting: Sorting, queryBuilder: QueryStringBuilder) {
        if (!sorting) return
        queryBuilder.add('sortBy', sorting.property)
        queryBuilder.add('sortDirection', sorting.direction)
    }

    private addFilters(filters: object, queryBuilder: QueryStringBuilder) {
        for (let [key, value] of Object.entries(filters)) {
            if (!value) continue
            queryBuilder.add(key, value)
        }
    }
}
