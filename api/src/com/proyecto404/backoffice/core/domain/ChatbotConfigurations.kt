package com.proyecto404.backoffice.core.domain

import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider

interface ChatbotConfigurations {
    fun update(configuration: ChatbotConfiguration)
    fun get(): ChatbotConfiguration
    fun add(configuration: ChatbotConfiguration)
}

fun RepositoryProvider.chatbotConfigurations() = this.get<ChatbotConfigurations>()
