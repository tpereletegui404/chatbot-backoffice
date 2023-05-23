package com.proyecto404.backoffice.e2e.security

import com.nbottarini.asimov.json.Json
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.e2e.testing.BaseApiTest
import com.proyecto404.backoffice.e2e.testing.returningId
import com.proyecto404.backoffice.e2e.testing.succeeds
import com.proyecto404.backoffice.core.security.domain.User
import com.proyecto404.backoffice.core.security.domain.UserExamples.alice
import com.proyecto404.backoffice.core.security.domain.users
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class LoginApiTest: BaseApiTest() {
    @Disabled
    @Test
    fun login() {
        val aliceId = createAlice()
        val aliceCredentialsJson = Json.obj("username" to alice.username, "password" to alice.password)

        val response = request().asAnonymous().post("/login", aliceCredentialsJson).exec()

        response.assertThat().succeeds()
        val updatedAlice = repositories.users().get(aliceId)
        assertThat(updatedAlice.sessionToken).isNotNull
        response.assertThat().body("user.id", equalTo(updatedAlice.id.toInt()))
        response.assertThat().body("user.username", equalTo(updatedAlice.username))
        response.assertThat().body("sessionToken", equalTo(updatedAlice.sessionToken))
        response.assertThat().body("isSuccessful", equalTo(true))
    }

    private fun createAlice(): Id<User> {
        val response = request().asAdmin().post("/users", aliceJson).exec()
        return response.returningId()
    }

    private val aliceJson by lazy {
        Json.obj(
            "username" to alice.username,
            "password" to alice.password,
        )
    }
}
