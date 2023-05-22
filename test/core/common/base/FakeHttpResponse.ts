import { HttpResponse } from '@/core/common/infrastructure/http/HttpResponse'
import { HttpMethod } from '@/core/common/infrastructure/http/HttpMethod'

export class FakeHttpResponse<T> implements HttpResponse<T> {
    body: T
    baseUrl = ''
    headers = {}
    method = HttpMethod.GET
    relativeUrl = ''
    requestData = null
    status = 200
    url = ''

    constructor(body: T) {
        this.body = body
    }
}
