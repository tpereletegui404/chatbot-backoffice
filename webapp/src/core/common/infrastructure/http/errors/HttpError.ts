import { HttpMethod } from '../HttpMethod'
import { BaseError } from '../../../base/lang/BaseError'

export class HttpError extends BaseError {
    private readonly _method: HttpMethod
    private readonly _url: string
    private readonly _status: number
    private readonly _statusText: string
    private readonly _data: any
    private readonly _headers: any
    private readonly _innerError: Error

    constructor(innerError: Error, method: HttpMethod, url: string, status: number, statusText: string, data: any, headers: any) {
        super(`Http error: ${status} - ${statusText}`)
        this._innerError = innerError
        this._method = method
        this._url = url
        this._status = status
        this._statusText = statusText
        this._data = data
        this._headers = headers
    }

    get method(): HttpMethod {
        return this._method
    }

    get url(): string {
        return this._url
    }

    get status(): number {
        return this._status
    }

    get statusText(): string {
        return this._statusText
    }

    get data(): any {
        return this._data
    }

    get headers(): any {
        return this._headers
    }

    get innerError(): Error {
        return this._innerError
    }
}
