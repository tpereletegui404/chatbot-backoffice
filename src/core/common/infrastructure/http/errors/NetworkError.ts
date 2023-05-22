import { BaseError } from '../../../base/lang/BaseError'


export class NetworkError extends BaseError {
    constructor(message: string, status: number) {
        super(`Network error: ${message} - ${status}`)
    }
}
