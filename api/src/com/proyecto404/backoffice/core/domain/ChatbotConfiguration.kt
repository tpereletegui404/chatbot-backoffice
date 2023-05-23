package com.proyecto404.backoffice.core.domain

import com.proyecto404.backoffice.base.domain.Id

class ChatbotConfiguration(
    val id: Id<ChatbotConfiguration>,
    val apiKey: String? = null,
    var context: String? = null,
    val maxTokens: Int,
    val temperature: Double,
    val topP: Double,
    val frequencyPenalty: Int,
    val parameterPresencePenalty: Int
    ) {

    fun replaceContext(context: String) {
        this.context = context
    }
}