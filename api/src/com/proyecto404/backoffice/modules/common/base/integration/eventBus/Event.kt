package com.proyecto404.backoffice.modules.common.base.integration.eventBus

import com.nbottarini.asimov.time.Clock
import java.util.*

abstract class Event(open val id: String = UUID.randomUUID().toString()) {
    val occurredOn = Clock.now()

    override fun equals(other: Any?) = other is Event && other.id == id

    override fun hashCode() = id.hashCode()

    override fun toString() = "${javaClass.name}('$id', occurredOn=$occurredOn)"
}
