package com.proyecto404.backoffice.base.http.server

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.values.JsonObject
import com.nbottarini.asimov.json.values.JsonValue
import io.javalin.http.Context

fun Context.jsonValue(json: JsonValue = JsonObject()) {
    contentType("application/json")
    result(json.toString())
}

fun Context.jsonObj(vararg pairs: Pair<String, Any?>) {
    jsonValue(Json.obj(pairs.toList()))
}

fun Context.jsonValue(value: Any?) {
    jsonValue(Json.value(value))
}

fun Context.jsonArray(vararg values: Any?) {
    jsonValue(Json.value(values))
}
