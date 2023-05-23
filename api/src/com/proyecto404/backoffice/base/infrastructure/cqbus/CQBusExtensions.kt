package com.proyecto404.backoffice.base.infrastructure.cqbus

import com.nbottarini.asimov.cqbus.CQBus
import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.core.security.app.identities.SystemIdentity

fun <T: Request<R>, R> CQBus.executeAsSystem(request: T): R =
    execute(request, ExecutionContext().withIdentity(SystemIdentity()))
