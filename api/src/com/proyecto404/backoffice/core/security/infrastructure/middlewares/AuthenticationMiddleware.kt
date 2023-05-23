package com.proyecto404.backoffice.core.security.infrastructure.middlewares

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.core.security.app.identities.UserIdentity
import com.proyecto404.backoffice.core.security.domain.Users

class AuthenticationMiddleware(private val users: Users): Middleware {
    override fun <T: Request<R>, R> execute(request: T, next: (T) -> R, context: ExecutionContext): R {
        handleAuthentication(context)
        return next(request)
    }

    private fun handleAuthentication(context: ExecutionContext) {
        val sessionToken = context["SessionToken"] as? String ?: return
        val user = users.findBy(sessionToken) ?: return
        context.identity = UserIdentity(user)
    }
}
