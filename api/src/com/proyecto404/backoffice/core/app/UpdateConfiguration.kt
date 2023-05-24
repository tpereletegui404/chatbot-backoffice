package com.proyecto404.backoffice.core.app

import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.PureCommand
import com.nbottarini.asimov.cqbus.requests.handlers.RequestHandler
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.core.domain.chatbotConfigurations

data class UpdateConfiguration(
    val apiKey: String? = null,
    var context: String? = null,
    val maxTokens: Int,
    val temperature: Double,
    val topP: Double,
    val frequencyPenalty: Int,
    val parameterPresencePenalty: Int
): PureCommand {
    internal class Handler(private val repositories: RepositoryProvider): RequestHandler<UpdateConfiguration, Unit> {

        override fun execute(request: UpdateConfiguration, identity: Identity) {
            val chatbot = repositories.chatbotConfigurations().get()
            chatbot.apiKey = request.apiKey
            chatbot.context = request.context
            chatbot.maxTokens = request.maxTokens
            chatbot.temperature = request.temperature
            chatbot.topP = request.topP
            chatbot.frequencyPenalty = request.frequencyPenalty
            chatbot.parameterPresencePenalty = request.parameterPresencePenalty
            repositories.chatbotConfigurations().update(chatbot)
        }
    }
}
