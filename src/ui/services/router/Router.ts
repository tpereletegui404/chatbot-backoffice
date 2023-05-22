import { ParsedUrlQuery } from 'querystring'

export interface Router {
    query: ParsedUrlQuery
    path: string

    navigate(route: string)
    replace(route: string)
}
