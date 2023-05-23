@file:Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")

package com.proyecto404.backoffice.base.infrastructure.persistence.memory

import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.integration.eventBus.EventBus
import com.proyecto404.backoffice.base.integration.eventBus.InProcessEventBus
import com.proyecto404.backoffice.core.security.domain.Users
import com.proyecto404.backoffice.core.security.infrastructure.memory.MemoryUsers
import kotlin.reflect.KClass

class MemoryRepositoryProvider(val eventBus: EventBus = InProcessEventBus()): RepositoryProvider() {
    override fun <T: Any> create(repositoryType: KClass<T>) = when(repositoryType) {
        Users::class -> MemoryUsers()
        else -> throw NotImplementedError("Memory${repositoryType.simpleName} not implemented")
    } as T
}
