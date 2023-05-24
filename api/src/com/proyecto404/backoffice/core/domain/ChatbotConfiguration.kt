package com.proyecto404.backoffice.core.domain

import com.proyecto404.backoffice.base.domain.Id

class ChatbotConfiguration(
    var id: Id<ChatbotConfiguration>,
    var apiKey: String? = null,
    var context: String? = null,
    var maxTokens: Int,
    var temperature: Double,
    var topP: Double,
    var frequencyPenalty: Int,
    var parameterPresencePenalty: Int
    ) {

    fun addContext(newContext: String) {
        this.context = "${this.context}\n$newContext"
    }
}