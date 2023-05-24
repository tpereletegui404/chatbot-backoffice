package com.proyecto404.backoffice.core.app

import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.PureCommand
import com.nbottarini.asimov.cqbus.requests.handlers.RequestHandler
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.core.domain.chatbotConfigurations

//@Authorization([Admin])
data class AddContext(
    private val context: String
): PureCommand {
    internal class Handler(
        private val repositories: RepositoryProvider,
    ): RequestHandler<AddContext, Unit> {

        override fun execute(request: AddContext, identity: Identity) {
            val chatbot = repositories.chatbotConfigurations().get()
            chatbot.replaceContext(request.context)
            repositories.chatbotConfigurations().update(chatbot)
        }
    }
}
