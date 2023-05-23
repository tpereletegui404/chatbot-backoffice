package com.proyecto404.backoffice.modules.common.base.transactions

class NullTransactionManager: TransactionManager {
    override fun beginTransaction(): Transaction {
        return NullTransaction()
    }
}
