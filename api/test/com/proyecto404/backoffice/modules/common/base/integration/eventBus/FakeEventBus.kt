package com.proyecto404.backoffice.modules.common.base.integration.eventBus

import com.proyecto404.backoffice.modules.common.base.integration.eventBus.Event
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.EventBus
import com.proyecto404.backoffice.modules.common.base.integration.eventBus.EventHandler

class FakeEventBus: EventBus() {
    val publishedEvents = mutableListOf<Event>()

    override fun publish(event: Event) {
        publishedEvents.add(event)
    }

    override fun publish(events: List<Event>) {
        publishedEvents.addAll(events)
    }

    override fun subscribe(handler: EventHandler) {}
}
