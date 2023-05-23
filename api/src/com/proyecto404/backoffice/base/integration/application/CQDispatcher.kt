package com.proyecto404.backoffice.base.integration.application

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.base.integration.eventBus.Event

interface CQDispatcher {
    fun <T: Request<R>, R> execute(request: T, context: ExecutionContext = ExecutionContext()): R
    fun notify(event: Event)
}
