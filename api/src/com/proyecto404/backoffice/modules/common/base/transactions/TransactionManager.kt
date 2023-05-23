package com.proyecto404.backoffice.modules.common.base.transactions

interface TransactionManager {
    fun beginTransaction(): Transaction
}

inline fun <R> TransactionManager.transactional(runnable: (Transaction) -> R): R {
    val transaction = beginTransaction()
    try {
        val result = runnable(transaction)
        if (!transaction.isClosed) transaction.commit()
        return result
    } catch (e: Throwable) {
        if (!transaction.isClosed) transaction.rollback()
        throw e
    }
}
