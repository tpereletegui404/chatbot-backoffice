package com.proyecto404.backoffice.core.security.infrastructure.middlewares

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.base.auth.UnauthorizedAccessError
import com.proyecto404.backoffice.core.security.app.authorization.authorizedRoles

class AuthorizationMiddleware: Middleware {
    override fun <T: Request<R>, R> execute(request: T, next: (T) -> R, context: ExecutionContext): R {
        failIfNotAuthorized(request.javaClass, context)
        return next(request)
    }

    private fun failIfNotAuthorized(clazz: Class<Any>, context: ExecutionContext) {
        val requiredRoles = getRequiredRoles(clazz)
        if (requiredRoles.isEmpty()) return
        val userRoles = getUserRoles(context)
        val hasARequiredRole = userRoles.any { requiredRoles.contains(it) }
        if (!hasARequiredRole) throw com.proyecto404.backoffice.base.auth.UnauthorizedAccessError()
    }

    private fun getRequiredRoles(clazz: Class<Any>) = authorizedRoles(clazz).map { it.name.lowercase() }

    private fun getUserRoles(context: ExecutionContext): List<String> {
        return context.identity.roles.map { it.lowercase() }
    }
}
