import { HttpClient } from '../HttpClient'
import axios, { AxiosAdapter, AxiosError, AxiosInstance, Method } from 'axios'
import { HttpResponse } from '../HttpResponse'
import { HttpMethod } from '../HttpMethod'
import { HttpError } from '../errors/HttpError'
import { NetworkError } from '../errors/NetworkError'
import { AuthenticationError } from '../errors/AuthenticationError'
import { HttpInterceptor } from '../HttpInterceptor'
import { HttpRequest } from '../HttpRequest'
import { ResponseFactory } from './ResponseFactory'
import { AxiosUrlHelper } from './AxiosUrlHelper'

export class AxiosHttpClient implements HttpClient {
    private readonly http: AxiosInstance
    private readonly baseUrl: string
    private readonly responseFactory = new ResponseFactory()
    private interceptors: HttpInterceptor[] = []
    private errorInterceptors: HttpInterceptor[] = [
        new NetworkErrorInterceptor(),
        new AuthenticationErrorInterceptor(),
    ]

    constructor(baseUrl: string, axiosAdapter: AxiosAdapter | undefined = axios.defaults.adapter) {
        this.baseUrl = baseUrl
        this.http = axios.create({
            baseURL: baseUrl,
            adapter: axiosAdapter,
            withCredentials: true,
        })
    }

    async get<T = any>(url: string, headers: { [key: string]: string } = {}): Promise<HttpResponse<T>> {
        return this.send<T>(this.request(HttpMethod.GET, url, null, headers))
    }

    async post<T = any>(url: string, data: any = null, headers: { [key: string]: string } = {}): Promise<HttpResponse<T>> {
        return this.send<T>(this.request(HttpMethod.POST, url, data, headers))
    }

    async put<T = any>(url: string, data: any = null, headers: { [key: string]: string } = {}): Promise<HttpResponse<T>> {
        return this.send<T>(this.request(HttpMethod.PUT, url, data, headers))
    }

    async delete<T = any>(url: string, headers: { [key: string]: string } = {}): Promise<HttpResponse<T>> {
        return this.send<T>(this.request(HttpMethod.DELETE, url, null, headers))
    }

    async patch<T = any>(url: string, data: any = null, headers: { [key: string]: string } = {}): Promise<HttpResponse<T>> {
        return this.send<T>(this.request(HttpMethod.PATCH, url, data, headers))
    }

    async head<T = any>(url: string, headers: { [key: string]: string } = {}): Promise<HttpResponse<T>> {
        return this.send<T>(this.request(HttpMethod.HEAD, url, null, headers))
    }

    private request(method: HttpMethod, url: string, data: any = null, headers: { [key: string]: string } = {}): HttpRequest {
        return { method, url, data, headers }
    }

    async send<T = any>(request: HttpRequest, onProgress?: (progress: number) => void): Promise<HttpResponse<T>> {
        try {
            await this.interceptRequest(request)
            const axiosResponse = await this.http.request({
                url: request.url,
                data: request.data,
                method: request.method as Method,
                headers: request.headers,
                baseURL: this.baseUrl,
                onUploadProgress: this.progressHandler(onProgress),
            })
            const response = this.responseFactory.create<T>(axiosResponse, request)
            await this.interceptResponse(response)
            return response
        } catch (e: any) {
            throw this.handleError(e, request)
        }
    }

    private progressHandler(onProgress?: (progress: number) => void) {
        if (!onProgress) return undefined
        return (progressEvent) => {
            const percent = Math.min((progressEvent.loaded * 100) / progressEvent.total, 100)
            onProgress(percent)
        }
    }

    private async interceptRequest(request: HttpRequest) {
        for (let interceptor of this.interceptors) {
            if (!interceptor.onRequest) continue
            await interceptor.onRequest(request)
        }
    }

    private async interceptResponse(response: HttpResponse<any>) {
        for (let interceptor of this.interceptors) {
            if (!interceptor.onResponse) continue
            await interceptor.onResponse(response)
        }
    }

    addInterceptor(interceptor: HttpInterceptor) {
        this.interceptors.push(interceptor)
        if (interceptor.onError) {
            this.errorInterceptors.push(interceptor)
        }
    }

    private handleError(e: AxiosError, request: HttpRequest): Error {
        let error: Error = this.createHttpError(e, request, this.baseUrl)
        for (let interceptor of this.errorInterceptors) {
            error = interceptor.onError!(error)
        }
        return error
    }

    private createHttpError(error: AxiosError, request: HttpRequest, baseUrl: string | undefined): HttpError {
        const urlHelper = new AxiosUrlHelper(baseUrl, request.url)
        const url = urlHelper.absoluteUrl
        const status = error.response?.status ?? 0
        const statusText = error.response?.statusText ?? ''
        const data = error.response?.data
        const headers = error.response?.headers ?? {}
        return new HttpError(error, request.method, url, status, statusText, data, headers)
    }
}

class NetworkErrorInterceptor implements HttpInterceptor {
    onError?(error: Error): Error {
        if (!(error instanceof HttpError) || !this.isNetworkError(error)) return error
        let message = error.method.toString() + ' ' + error.url
        return new NetworkError(message, error.status)
    }

    private isNetworkError(error: HttpError) {
        return error.innerError.message === 'Network Error' || [502, 503, 504].contains(error.status)
    }
}

class AuthenticationErrorInterceptor implements HttpInterceptor {
    onError?(error: Error): Error {
        if (!(error instanceof HttpError) || !this.isAuthenticationError(error)) return error
        return new AuthenticationError(error)
    }

    private isAuthenticationError(error: HttpError): boolean {
        return [401, 403].contains(error.status)
    }
}
