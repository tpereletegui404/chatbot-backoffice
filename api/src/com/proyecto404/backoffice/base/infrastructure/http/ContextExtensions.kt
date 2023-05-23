package com.proyecto404.backoffice.base.infrastructure.http

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.parser.JsonParseError
import com.nbottarini.asimov.json.values.JsonObject
import io.javalin.http.Context

fun Context.json(): JsonObject {
    try {
        return Json.parse(this.body()).asObject()!!
    } catch (e: NullPointerException) {
        throw invalidJsonError(this.body())
    } catch (e: IllegalArgumentException) {
        throw invalidJsonError(this.body())
    } catch (e: JsonParseError) {
        throw invalidJsonError(this.body())
    }
}

private fun invalidJsonError(json: String?) = JsonParseError("Invalid json '$json'")
