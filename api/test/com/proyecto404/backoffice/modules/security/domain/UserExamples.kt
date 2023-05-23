@file:Suppress("ClassName")

package com.proyecto404.backoffice.modules.security.domain

import com.proyecto404.backoffice.modules.common.base.testing.TestExamples
import com.proyecto404.backoffice.modules.security.app.authorization.Roles

object UserExamples {
    val usernames = TestExamples("alice", "bob", "charly", "david", "eric")
    object alice {
        val username = "alice"
        val password = "1234"
        val sessionToken = "aliceS3ss10n"
        val roles = listOf(Roles.ProductOwner, Roles.Administrative)
        val salt = "alice salt"
    }
}
