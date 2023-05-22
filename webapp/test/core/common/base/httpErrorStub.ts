import { HttpError } from '../../../../src/core/common/infrastructure/http/errors/HttpError'
import { HttpMethod } from '../../../../src/core/common/infrastructure/http/HttpMethod'

export function httpError(type: string, message: string = '', code = 400) {
    return new HttpError(new Error(), HttpMethod.GET, 'http://some.url', code, 'Bad Request', { type, message }, [])
}

export function httpServerError() {
    return new HttpError(new Error(), HttpMethod.GET, 'http://some.url', 500, 'Internal Server Error', { }, [])
}
