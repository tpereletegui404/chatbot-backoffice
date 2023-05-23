package com.proyecto404.backoffice.modules.common.base.http.client

interface HttpClient {
    fun post(request: HttpRequest): HttpResponse
    fun get(request: HttpRequest): HttpResponse
}
