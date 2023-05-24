package com.proyecto404.backoffice.e2e

import com.nbottarini.asimov.json.Json
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.domain.ChatbotConfiguration
import com.proyecto404.backoffice.core.domain.chatbotConfigurations
import com.proyecto404.backoffice.e2e.testing.BaseApiTest
import com.proyecto404.backoffice.e2e.testing.succeeds
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test

class ChatbotApiTest: BaseApiTest() {
    @Test
    fun create() {
        val response = request().asAnonymous().post("/configuration", addContextJson).exec()

        response.assertThat().succeeds(201)
        val chatbot = getChatbotConfiguration()
        assertEqualsChatbotExample(chatbot)
    }

    @Test
    fun get() {
        val configuration = addChatbotConfiguration()

        val response = request().asAnonymous().get("/configuration").exec()

        response.assertThat().succeeds(200)
        assertEqualsResponseBody(response, configuration)
    }

    private fun addChatbotConfiguration(): ChatbotConfiguration {
        val configuration = ChatbotConfiguration(
            Id(1),
            "some key",
            "some context",
            20,
            0.5,
            0.4,
            1,
            1
        )
        repositories.chatbotConfigurations().add(configuration)
        return configuration
    }

    private fun getChatbotConfiguration() = repositories.chatbotConfigurations().get()

    private fun assertEqualsChatbotExample(chatbotConfiguration: ChatbotConfiguration) {
        assertThat(chatbotConfiguration.context).isEqualTo(addContextJson["context"]?.asString())
        assertThat(chatbotConfiguration.topP).isEqualTo(addContextJson["topP"]?.asDouble())
        assertThat(chatbotConfiguration.frequencyPenalty).isEqualTo(addContextJson["frequencyPenalty"]?.asInt())
        assertThat(chatbotConfiguration.temperature).isEqualTo(addContextJson["temperature"]?.asDouble())
        assertThat(chatbotConfiguration.parameterPresencePenalty).isEqualTo(addContextJson["parameterPresencePenalty"]?.asInt())
    }

    private fun assertEqualsResponseBody(response: ValidatableResponse, config: ChatbotConfiguration) {
        assertThat(response.body("context", equalTo(config.context)))
        assertThat(response.body("topP", equalTo(config.topP.toFloat())))
        assertThat(response.body("frequencyPenalty", equalTo(config.frequencyPenalty)))
        assertThat(response.body("temperature", equalTo(config.temperature.toFloat())))
        assertThat(response.body("parameterPresencePenalty", equalTo(config.parameterPresencePenalty)))
    }

    private val addContextJson by lazy {
        Json.obj(
            "context" to "some context",
            "maxTokens" to 400,
            "temperature" to 0.7,
            "topP" to 0.2,
            "frequencyPenalty" to 0,
            "parameterPresencePenalty" to 0
        )
    }
}
