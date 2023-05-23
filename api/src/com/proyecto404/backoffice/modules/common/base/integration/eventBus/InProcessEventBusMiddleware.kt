package com.proyecto404.backoffice.modules.common.base.integration.eventBus

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request

class InProcessEventBusMiddleware(private val eventBus: InProcessEventBus): Middleware {
    override fun <T : Request<R>, R> execute(request: T, next: (T) -> R, context: ExecutionContext): R {
        eventBus.preRequest()
        val response = next(request)
        eventBus.postRequest()
        return response
    }
}
