package com.proyecto404.backoffice.base.infrastructure.middlewares

import com.nbottarini.asimov.cqbus.ExecutionContext
import com.nbottarini.asimov.cqbus.Middleware
import com.nbottarini.asimov.cqbus.requests.Request
import com.proyecto404.backoffice.base.transactions.TransactionManager
import com.proyecto404.backoffice.base.transactions.transactional

class TransactionalMiddleware(private val transactionManager: TransactionManager): Middleware {
    override fun <T: Request<R>, R> execute(request: T, next: (T) -> R, context: ExecutionContext): R {
        return transactionManager.transactional { next(request) }
    }
}
