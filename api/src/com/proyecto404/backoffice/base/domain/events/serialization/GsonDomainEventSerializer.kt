package com.proyecto404.backoffice.base.domain.events.serialization

import com.google.gson.JsonSyntaxException
import com.proyecto404.backoffice.base.domain.events.DomainEvent
import com.proyecto404.backoffice.base.serialization.Serializer
import com.proyecto404.backoffice.base.serialization.gson.GsonSerializer

class GsonDomainEventSerializer(private val serializer: Serializer = GsonSerializer()): DomainEventSerializer {
    override fun serialize(event: DomainEvent): String {
        val serializedEvent = serializer.serialize(event)
        return serializer.serialize(StoredEvent(event.javaClass.name, serializedEvent))
    }

    override fun deserialize(serialized: String): DomainEvent {
        val storedEvent = deserialize(serialized, StoredEvent::class.java)
        val eventClass = getEventClass(storedEvent)
        return deserialize(storedEvent.serializedEvent, eventClass)
    }

    private fun <T> deserialize(serialized: String, type: Class<T>): T {
        try {
            return serializer.deserialize(serialized, type)
                ?: throw DomainEventDeserializationError("Event deserialization failed: invalid json syntax")
        } catch (e: JsonSyntaxException) {
            throw DomainEventDeserializationError("Event deserialization failed: invalid json syntax", e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getEventClass(storedEvent: StoredEvent): Class<DomainEvent> {
        return try {
            Class.forName(storedEvent.eventClass) as Class<DomainEvent>
        } catch (e: NullPointerException) {
            throw DomainEventDeserializationError("Event class not found: ${storedEvent.eventClass}", e)
        } catch (e: ClassNotFoundException) {
            throw DomainEventDeserializationError("Event class not found: ${storedEvent.eventClass}", e)
        }
    }

    class StoredEvent(val eventClass: String, val serializedEvent: String)
}
