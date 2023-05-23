package com.proyecto404.backoffice.modules.common.base.integration.eventBus

abstract class EventBus {
    abstract fun publish(event: Event)
    abstract fun publish(events: List<Event>)
    abstract fun subscribe(handler: EventHandler)
}
