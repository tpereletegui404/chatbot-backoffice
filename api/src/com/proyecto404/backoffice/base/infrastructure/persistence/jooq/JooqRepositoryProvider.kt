@file:Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")

package com.proyecto404.backoffice.base.infrastructure.persistence.jooq

import com.proyecto404.backoffice.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.integration.eventBus.EventBus
import com.proyecto404.backoffice.base.integration.eventBus.InProcessEventBus
import com.proyecto404.backoffice.core.domain.ChatbotConfigurations
import com.proyecto404.backoffice.core.infrastructure.persistance.jooq.JooqChatbotConfigurations
import com.proyecto404.backoffice.core.security.domain.Users
import com.proyecto404.backoffice.core.security.infrastructure.jooq.JooqUsers
import kotlin.reflect.KClass

class JooqRepositoryProvider(private val jooq: JooqConfiguration, val eventBus: EventBus = InProcessEventBus()): RepositoryProvider() {
    override fun <T: Any> create(repositoryType: KClass<T>) = when(repositoryType) {
        Users::class -> JooqUsers(jooq)
        ChatbotConfigurations::class -> JooqChatbotConfigurations(jooq)
        else -> throw NotImplementedError("Jooq${repositoryType.simpleName} not implemented")
    } as T
}
