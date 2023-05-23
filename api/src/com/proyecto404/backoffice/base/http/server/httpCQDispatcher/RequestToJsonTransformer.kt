package com.proyecto404.backoffice.base.http.server.httpCQDispatcher

import com.nbottarini.asimov.json.values.JsonObject
import io.javalin.http.Context
import kotlin.reflect.KClass

interface RequestToJsonTransformer {
    fun transform(context: Context, json: JsonObject?, type: KClass<*>)
}
