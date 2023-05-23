package com.proyecto404.backoffice.base.domain.events.serialization

import com.proyecto404.backoffice.base.domain.events.DomainEvent

interface DomainEventSerializer {
    fun serialize(event: DomainEvent): String
    fun deserialize(serialized: String): DomainEvent
}
