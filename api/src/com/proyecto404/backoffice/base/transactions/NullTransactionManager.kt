package com.proyecto404.backoffice.base.transactions

class NullTransactionManager: TransactionManager {
    override fun beginTransaction(): Transaction {
        return NullTransaction()
    }
}
