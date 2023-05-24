package com.proyecto404.backoffice.core.infrastructure.persistance.jooq

import com.proyecto404.backoffice.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.domain.ChatbotConfiguration
import com.proyecto404.backoffice.core.domain.ChatbotConfigurations
import com.proyecto404.backoffice.infrastructure.jooq.generated.Sequences
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.daos.ChatbotConfigurationsDao
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.pojos.ChatbotConfigurationsDto

class JooqChatbotConfigurations(val jooq: JooqConfiguration): ChatbotConfigurations {
    private val dao = ChatbotConfigurationsDao(jooq)

    override fun update(configuration: ChatbotConfiguration) {
        dao.update(configuration.toDto())
    }

    override fun get(): ChatbotConfiguration {
        return dao.findAll().single().toChatbotConfiguration()
    }

    override fun add(configuration: ChatbotConfiguration) {
        dao.insert(configuration.toDto())
    }

    override fun nextId() = Id<ChatbotConfiguration>(jooq.dsl().nextval(Sequences.CHATBOT_CONFIGURATIONS_ID_SEQ))

    private fun ChatbotConfigurationsDto.toChatbotConfiguration() = ChatbotConfiguration(
        Id(id),
        apiKey,
        context,
        maxTokens,
        temperature.toDouble(),
        topP.toDouble(),
        frequencyPenalty,
        parameterpresencePenalty
    )

    private fun ChatbotConfiguration.toDto() = ChatbotConfigurationsDto().also {
        it.id = id.toInt()
        it.apiKey = apiKey
        it.context = context
        it.maxTokens = maxTokens
        it.temperature = temperature.toBigDecimal()
        it.topP = topP.toBigDecimal()
        it.frequencyPenalty = frequencyPenalty
        it.parameterpresencePenalty = parameterPresencePenalty

    }
}

