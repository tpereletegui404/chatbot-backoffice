package com.proyecto404.backoffice.core.app

import com.nbottarini.asimov.cqbus.identity.Identity
import com.nbottarini.asimov.cqbus.requests.Query
import com.nbottarini.asimov.cqbus.requests.handlers.RequestHandler
import com.proyecto404.backoffice.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.core.domain.ChatbotConfiguration
import com.proyecto404.backoffice.core.domain.chatbotConfigurations

//@Authorization([Admin])
class GetConfiguration: Query<ChatbotConfiguration> {
    internal class Handler(
        private val repositories: RepositoryProvider
    ): RequestHandler<GetConfiguration, ChatbotConfiguration> {

        override fun execute(request: GetConfiguration, identity: Identity): ChatbotConfiguration {
            return repositories.chatbotConfigurations().get()
        }
    }
}
