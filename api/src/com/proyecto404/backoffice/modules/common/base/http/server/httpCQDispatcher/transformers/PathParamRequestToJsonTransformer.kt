package com.proyecto404.backoffice.modules.common.base.http.server.httpCQDispatcher.transformers

import com.nbottarini.asimov.json.values.JsonObject
import com.proyecto404.backoffice.modules.common.base.http.server.httpCQDispatcher.RequestToJsonTransformer
import io.javalin.http.Context
import kotlin.reflect.KClass

class PathParamRequestToJsonTransformer: RequestToJsonTransformer {
    override fun transform(context: Context, json: JsonObject?, type: KClass<*>) {
        context.pathParamMap().forEach { json?.set(it.key, it.value) }
    }
}
