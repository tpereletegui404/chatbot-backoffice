package com.proyecto404.backoffice.modules.common.infrastructure.http

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.parser.JsonParseError
import com.nbottarini.asimov.json.values.JsonObject
import com.nbottarini.asimov.json.values.JsonValue
import com.proyecto404.backoffice.modules.common.base.http.client.HttpResponse

fun HttpResponse.json(): JsonObject {
    try {
        return Json.parse(this.body ?: "").asObject()!!
    } catch (e: NullPointerException) {
        throw invalidJsonError(this.body)
    } catch (e: IllegalArgumentException) {
        throw invalidJsonError(this.body)
    } catch (e: JsonParseError) {
        throw invalidJsonError(this.body)
    }
}

fun HttpResponse.jsonValue(): JsonValue {
    try {
        return Json.parse(this.body ?: "")
    } catch (e: IllegalArgumentException) {
        throw invalidJsonError(this.body)
    } catch (e: JsonParseError) {
        throw invalidJsonError(this.body)
    }
}

private fun invalidJsonError(json: String?) = JsonParseError("Invalid json '$json'")

fun HttpResponse.requireSuccess() {
    if (status != 200) throw IllegalStateException("Request failed with status $status")
}
