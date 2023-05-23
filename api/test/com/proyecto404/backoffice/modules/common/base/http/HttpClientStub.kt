package com.proyecto404.backoffice.modules.common.base.http

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.values.JsonObject
import com.nbottarini.asimov.json.values.JsonValue
import com.proyecto404.backoffice.modules.common.base.domain.errors.InvalidOperationError
import com.proyecto404.backoffice.modules.common.base.http.HttpClientStub.HttpMethods.GET
import com.proyecto404.backoffice.modules.common.base.http.HttpClientStub.HttpMethods.POST
import com.proyecto404.backoffice.modules.common.base.http.client.HttpClient
import com.proyecto404.backoffice.modules.common.base.http.client.HttpRequest
import com.proyecto404.backoffice.modules.common.base.http.client.HttpResponse

class HttpClientStub(private val baseUrl: String = ""): HttpClient {
    private val futureRequests = mutableListOf<FutureRequest>()
    val requests = mutableListOf<Request>()

    fun get(path: String) = Expectation(GET, path)

    fun post(path: String) = Expectation(POST, path)

    override fun post(request: HttpRequest) = doRequest(POST, request)

    override fun get(request: HttpRequest) = doRequest(GET, request)

    private fun doRequest(method: HttpMethods, request: HttpRequest): HttpResponse {
        val path = request.url.removePrefix(baseUrl)
        val futureRequest = futureRequests.firstOrNull { it.method == method && it.path == path }
            ?: throw InvalidOperationError("$method $path expectation not configured")
        requests.add(
            Request(
            futureRequest.method,
            path,
            Json.parse(request.body ?: "{}").asObject()!!,
            futureRequest.response,
            futureRequest.statusCode
        )
        )
        return HttpResponse(futureRequest.statusCode, futureRequest.response.toString())
    }

    inner class Expectation(val method: HttpMethods, val path: String) {
        fun returns(response: JsonValue) {
            futureRequests.add(FutureRequest(method, path, response, 200))
        }

        fun fails(statusCode: Int) {
            futureRequests.add(FutureRequest(method, path, Json.obj(), statusCode))
        }
    }

    data class FutureRequest(val method: HttpMethods, val path: String, val response: JsonValue, val statusCode: Int)

    data class Request(
        val method: HttpMethods,
        val path: String,
        val params: JsonObject,
        val response: JsonValue,
        val statusCode: Int,
    )

    enum class HttpMethods { POST, GET }
}
