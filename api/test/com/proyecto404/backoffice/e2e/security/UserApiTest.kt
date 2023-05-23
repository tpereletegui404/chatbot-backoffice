package com.proyecto404.backoffice.e2e.security

import com.nbottarini.asimov.json.Json
import com.proyecto404.backoffice.e2e.testing.BaseApiTest
import com.proyecto404.backoffice.e2e.testing.returningId
import com.proyecto404.backoffice.e2e.testing.succeeds
import com.proyecto404.backoffice.modules.common.base.domain.Id
import com.proyecto404.backoffice.modules.security.domain.User
import com.proyecto404.backoffice.modules.security.domain.UserExamples.alice
import com.proyecto404.backoffice.modules.security.domain.users
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class UserApiTest: BaseApiTest() {
    @Disabled
    @Test
    fun create() {
        val response = request().asAdmin().post("/users", aliceJson).exec()

        response.assertThat().succeeds(201)
        val user = getUser(response.returningId())
        assertThat(user.username).isEqualTo(alice.username)
        assertThat(user.roles).containsExactlyInAnyOrderElementsOf(alice.roles)
    }

    private fun getUser(id: Id<User>) = repositories.users().get(id)

    private val aliceJson by lazy {
        Json.obj(
            "username" to alice.username,
            "password" to alice.password,
            "roles" to alice.roles.map { it.toString() }
        )
    }
}
