package com.proyecto404.backoffice.base.domain.events.serialization

class DomainEventDeserializationError(message: String, cause: Throwable? = null): Throwable(message, cause)
