package com.proyecto404.backoffice.base.integration.eventBus

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
