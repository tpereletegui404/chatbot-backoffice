package com.proyecto404.backoffice.core.app

import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.common.infrastructure.persistance.jooq.BaseJooqTest
import com.proyecto404.backoffice.core.domain.ChatbotConfiguration
import com.proyecto404.backoffice.core.domain.chatbotConfigurations
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AddContextTest: BaseJooqTest() {
    @Test
    fun `should concat given context to the actual config`() {
        addChatbotConfiguration(context = "default context")
        val contextToAdd = "added 1 item"

        handler.execute(AddContext(contextToAdd))

        val config = repositories.chatbotConfigurations().get()
        assertThat(config.context).isEqualTo("default context\nadded 1 item")
    }

    private fun addChatbotConfiguration(context: String): ChatbotConfiguration {
        val configuration = ChatbotConfiguration(Id(1), null, context, 400, 0.7, 0.2, 0, 0)
        repositories.chatbotConfigurations().add(configuration)
        return configuration
    }

    private val handler = AddContext.Handler(repositories)
}
