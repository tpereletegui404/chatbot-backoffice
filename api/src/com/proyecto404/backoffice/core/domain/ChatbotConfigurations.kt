package com.proyecto404.backoffice.core.domain

import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.domain.Id

interface ChatbotConfigurations {
    fun update(configuration: ChatbotConfiguration)
    fun get(): ChatbotConfiguration
    fun add(configuration: ChatbotConfiguration)
    fun nextId(): Id<ChatbotConfiguration>
}

fun RepositoryProvider.chatbotConfigurations() = this.get<ChatbotConfigurations>()
