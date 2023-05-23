package com.proyecto404.backoffice.e2e.testing

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.values.JsonValue
import com.proyecto404.backoffice.modules.common.base.domain.Id
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers

fun RequestSpecification.jsonBody(json: JsonValue): RequestSpecification {
    return this.body(json.toString())
}

fun RequestSpecification.jsonBody(vararg pairs: Pair<String, Any?>): RequestSpecification {
    return jsonBody(Json.obj(pairs.toList()))
}

fun ValidatableResponse.succeeds(statusCode: Int = 200): ValidatableResponse = this.statusCode(statusCode)

fun ValidatableResponse.failsWith(statusCode: Int): ValidatableResponse = this.statusCode(statusCode)

fun ValidatableResponse.failsWithForbidden(): ValidatableResponse = this.statusCode(403)

fun ValidatableResponse.failsWithNotFound(): ValidatableResponse = this.statusCode(404)

fun <T> ValidatableResponse.returningId(): Id<T> {
    val id = this.body("id", CoreMatchers.notNullValue()).extract().path<Int>("id")
    return Id(id)
}
