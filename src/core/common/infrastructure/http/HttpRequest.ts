import { HttpMethod } from './HttpMethod'
import { Nullable } from '@/core/common/base/lang/Nullable'

export class HttpRequest {
    constructor(
        public method: HttpMethod,
        public url: string,
        public data: Nullable<any> = null,
        public headers: { [key: string]: string } = {},
    ) {
    }
}
