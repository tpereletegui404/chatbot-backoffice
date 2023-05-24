package com.proyecto404.backoffice.core.app

import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.Command
import com.nbottarini.asimov.cqbus.requests.handlers.RequestHandler
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.base.integration.application.CreateResponse
import com.proyecto404.backoffice.core.domain.ChatbotConfiguration
import com.proyecto404.backoffice.core.domain.chatbotConfigurations

data class CreateConfiguration(
    val apiKey: String? = null,
    var context: String? = null,
    val maxTokens: Int,
    val temperature: Double,
    val topP: Double,
    val frequencyPenalty: Int,
    val parameterPresencePenalty: Int
): Command<CreateResponse<ChatbotConfiguration>> {
    internal class Handler(
        private val repositories: RepositoryProvider
    ): RequestHandler<CreateConfiguration, CreateResponse<ChatbotConfiguration>> {

        override fun execute(request: CreateConfiguration, identity: Identity): CreateResponse<ChatbotConfiguration> {
            val chatbot = configFrom(request)
            repositories.chatbotConfigurations().add(chatbot)
            return CreateResponse(chatbot.id)
        }

        private fun configFrom(request: CreateConfiguration) =
            ChatbotConfiguration(
                repositories.chatbotConfigurations().nextId(),
                request.apiKey,
                request.context,
                request.maxTokens,
                request.temperature,
                request.topP,
                request.frequencyPenalty,
                request.parameterPresencePenalty
            )
    }
}
