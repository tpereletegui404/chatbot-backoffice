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
        addChatbotConfiguration()

        val response = request().asAnonymous().post("/configuration/context", addContextJson).exec()

        response.assertThat().succeeds(200)
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
    }

    private fun assertEqualsResponseBody(response: ValidatableResponse, config: ChatbotConfiguration) {
        assertThat(response.body("context", equalTo(config.context)))
    }

    private val addContextJson by lazy {
        Json.obj(
            "context" to "some context",
        )
    }
}
