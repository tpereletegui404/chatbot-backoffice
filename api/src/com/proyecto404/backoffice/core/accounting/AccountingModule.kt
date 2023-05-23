package com.proyecto404.backoffice.core.accounting

import com.proyecto404.backoffice.Core

class AccountingModule(coreConfig: Core.Config, coreServices: Core.Services) {
    private val cqBus = coreServices.cqBus
    private val repositories = coreServices.repositories
    private val jooq = coreServices.jooq
    private val eventBus = coreServices.eventBus

    init {
        registerHandlers()
        registerEventHandlers()
    }

    private fun registerHandlers() = with(cqBus) {

    }

    private fun registerEventHandlers() = with(eventBus) {

    }
}
