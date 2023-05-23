package com.proyecto404.backoffice.modules.common.infrastructure.cqbus

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request
import org.slf4j.LoggerFactory

class LoggingMiddleware: Middleware {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)

    override fun <T: Request<R>, R> execute(request: T, next: (T) -> R, context: ExecutionContext): R {
        logger.info("Executing use case $request" )
        return next(request)
    }
}
