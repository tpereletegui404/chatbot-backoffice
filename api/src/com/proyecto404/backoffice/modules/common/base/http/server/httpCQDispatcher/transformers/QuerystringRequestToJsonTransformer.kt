package com.proyecto404.backoffice.modules.common.base.http.server.httpCQDispatcher.transformers

import com.nbottarini.asimov.json.Json
import com.nbottarini.asimov.json.values.JsonObject
import com.proyecto404.backoffice.modules.common.base.http.server.httpCQDispatcher.RequestToJsonTransformer
import io.javalin.http.Context
import kotlin.reflect.KClass

class QuerystringRequestToJsonTransformer: RequestToJsonTransformer {
    override fun transform(context: Context, json: JsonObject?, type: KClass<*>) {
        context.queryParamMap().forEach {
            if (it.key.contains("[]")) json?.set(it.key.replace("[]", ""), Json.array(it.value))
            else json?.set(it.key, it.value.firstOrNull())
        }
    }
}
