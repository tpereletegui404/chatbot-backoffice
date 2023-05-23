package com.proyecto404.backoffice.e2e.testing

import com.nbottarini.asimov.json.values.JsonValue
import com.proyecto404.backoffice.core.security.domain.User
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification

class RequestBuilder(private val baseUrl: String = "") {
    private val requestSpecification = RestAssured.given()
    private var whenAction: ((RequestSpecification) -> Response)? = null

    fun asUser(user: User) = apply { requestSpecification.header("Authorization", "Bearer ${user.sessionToken}") }

    fun asAnonymous() = apply {}

    fun withHeader(name: String, value: String) = apply { requestSpecification.header(name, value) }

    fun body(json: JsonValue) = apply { requestSpecification.jsonBody(json) }

    fun post(endpoint: String, body: JsonValue? = null) = apply {
        whenAction = { it.post(baseUrl + endpoint) }
        if (body != null) body(body)
    }

    fun delete(endpoint: String, body: JsonValue? = null) = apply {
        whenAction = { it.delete(baseUrl + endpoint) }
        if (body != null) body(body)
    }

    fun put(endpoint: String, body: JsonValue? = null) = apply {
        whenAction = { it.put(baseUrl + endpoint) }
        if (body != null) body(body)
    }

    fun patch(endpoint: String, body: JsonValue? = null) = apply {
        whenAction = { it.patch(baseUrl + endpoint) }
        if (body != null) body(body)
    }

    fun get(endpoint: String) = apply {
        whenAction = { it.get(baseUrl + endpoint) }
    }

    fun exec(): ValidatableResponse {
        if (whenAction == null) throw Exception("Must define an action (post, get, put, ...)")
        return whenAction!!(requestSpecification.`when`()).then()
    }
}
