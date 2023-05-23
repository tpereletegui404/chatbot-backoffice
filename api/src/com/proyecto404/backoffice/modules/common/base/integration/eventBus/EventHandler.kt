package com.proyecto404.backoffice.modules.common.base.integration.eventBus

import com.proyecto404.backoffice.modules.common.base.integration.eventBus.Event
import kotlin.reflect.KClass

interface EventHandler {
    val eventTypes: List<KClass<*>>

    fun on(event: Event)
}
