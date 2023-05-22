import { HttpClient } from '../HttpClient'
import { HttpError } from './HttpError'
import { unhandledPromiseProxy } from '../../../base/lang/unhandledPromiseProxy'


export type ErrorMappings = { [key: string]: any }

export function httpErrorDecorator(httpClient: HttpClient, errorMappings: ErrorMappings): HttpClient {
    return unhandledPromiseProxy(httpClient, (e) => {
        for (const errorType of Object.keys(errorMappings)) {
            if (e instanceof HttpError && e.data.type === errorType) {
                throw new errorMappings[errorType](e.data.message)
            }
        }
        throw e
    })
}
