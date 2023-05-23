package com.proyecto404.backoffice.base.http.server.httpCQDispatcher

import com.google.gson.JsonParseException
import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.requests.Request
import com.nbottarini.asimov.json.Json
import com.proyecto404.backoffice.base.http.server.httpCQDispatcher.transformers.PathParamRequestToJsonTransformer
import com.proyecto404.backoffice.base.http.server.httpCQDispatcher.transformers.QuerystringRequestToJsonTransformer
import com.proyecto404.backoffice.base.integration.application.CQDispatcher
import com.proyecto404.backoffice.base.integration.eventBus.Event
import com.proyecto404.backoffice.base.infrastructure.serialization.JsonSerializer
import io.javalin.http.Context
import kotlin.reflect.KClass

class HttpCQDispatcher(private val dispatcher: CQDispatcher) {
    private val serializer = JsonSerializer()
    private val transformers = mutableListOf(
        QuerystringRequestToJsonTransformer(),
        PathParamRequestToJsonTransformer(),
    )

    inline fun <reified T: Request<*>> execute(ctx: Context, statusCode: Int = 200) {
        execute(T::class, ctx, statusCode)
    }

    fun notify(event: Event) { dispatcher.notify(event) }

    fun <T: Request<*>> execute(actionClass: KClass<T>, ctx: Context, statusCode: Int = 200) {
        val action = ctx.deserializedBody(actionClass)
        val actionResponse = execute(action, ctx)
        ctx.serialized(actionResponse, statusCode)
    }

    fun <R> execute(action: Request<R>, ctx: Context) = dispatcher.execute(action, ExecutionContext().with(ctx))

    fun <T: Any> Context.deserializedBody(type: KClass<T>): T {
        val json = jsonWithRequestParameters(type)
        try {
            return serializer.deserialize(json, type.java)
        } catch (e: Throwable) {
            throw JsonParseException(e.message, e)
        }
    }

    private fun Context.jsonWithRequestParameters(type: KClass<*>): String {
        var body = body()
        if (body.isEmpty()) body = "{}"
        val json = Json.parse(body).asObject()

        transformers.forEach { it.transform(this, json, type) }

        return json.toString()
    }

    fun addRequestToJsonTransformer(transformer: RequestToJsonTransformer) {
        transformers.add(transformer)
    }

    fun Context.serialized(obj: Any?, statusCode: Int = 200) {
        contentType("application/json")
        status(statusCode)
        if (obj != null) result(serializer.serialize(obj))
    }
}
