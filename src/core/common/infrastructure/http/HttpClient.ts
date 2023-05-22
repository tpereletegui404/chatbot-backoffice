import { HttpResponse } from './HttpResponse'
import { HttpInterceptor } from './HttpInterceptor'
import { HttpRequest } from './HttpRequest'

export interface HttpClient {
    get<T = any>(url: string, headers?: { [key: string]: string }): Promise<HttpResponse<T>>;

    post<T = any>(url: string, data: any, headers?: { [key: string]: string }): Promise<HttpResponse<T>>;

    put<T = any>(url: string, data: any, headers?: { [key: string]: string }): Promise<HttpResponse<T>>;

    patch<T = any>(url: string, data: any, headers?: { [key: string]: string }): Promise<HttpResponse<T>>;

    delete<T = any>(url: string, headers?: { [key: string]: string }): Promise<HttpResponse<T>>;

    head<T = any>(url: string, headers?: { [key: string]: string }): Promise<HttpResponse<T>>;

    send<T = any>(request: HttpRequest, onProgress?: (progress: number) => void): Promise<HttpResponse<T>>;

    addInterceptor(interceptor: HttpInterceptor);
}
