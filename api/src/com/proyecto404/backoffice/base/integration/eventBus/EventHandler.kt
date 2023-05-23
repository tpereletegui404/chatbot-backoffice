package com.proyecto404.backoffice.base.integration.eventBus

import kotlin.reflect.KClass

interface EventHandler {
    val eventTypes: List<KClass<*>>

    fun on(event: Event)
}
