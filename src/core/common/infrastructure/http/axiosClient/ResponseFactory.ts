import { AxiosResponse } from 'axios'
import { HttpResponse } from '../HttpResponse'
import { AxiosUrlHelper } from './AxiosUrlHelper'
import { HttpRequest } from '../HttpRequest'

export class ResponseFactory {
    create<T = any>(axiosResponse: AxiosResponse, request: HttpRequest): HttpResponse<T> {
        const urlHelper = new AxiosUrlHelper(axiosResponse.config.baseURL, axiosResponse.config.url)
        return {
            method: request.method,
            status: axiosResponse.status,
            body: axiosResponse.data,
            baseUrl: urlHelper.baseUrl,
            relativeUrl: urlHelper.relativeUrl,
            url: urlHelper.absoluteUrl,
            headers: axiosResponse.headers,
            requestData: request.data,
        }
    }
}
