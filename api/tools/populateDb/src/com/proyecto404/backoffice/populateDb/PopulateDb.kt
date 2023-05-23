package com.proyecto404.backoffice.populateDb

import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials.JdbcCredentials
import com.proyecto404.backoffice.modules.common.base.data.jooq.jooqConfiguration
import com.proyecto404.backoffice.modules.common.base.data.repositories.RepositoryProvider
import com.proyecto404.backoffice.modules.common.domain.ScenarioBuilder
import com.proyecto404.backoffice.modules.common.infrastructure.persistence.jooq.JooqRepositoryProvider
import com.proyecto404.backoffice.modules.security.infrastructure.passwords.SHA512PasswordEncryptor
import com.proyecto404.backoffice.populateDb.scenarios.DefaultScenario

class PopulateDb {
    private lateinit var credentials: JdbcCredentials
    private lateinit var repositories: RepositoryProvider

    fun run(args: Args) {
        println("Populating db with scenario ${args.scenario}...")
        repositories = createRepositories()
        runScenario(args.scenario)
    }

    private fun createRepositories(): RepositoryProvider {
        return JooqRepositoryProvider(jooqConfiguration {
            credentials = credentialsFromEnv()
        })
    }

    private fun runScenario(scenario: String) {
        val scenarioBuilder = ScenarioBuilder(repositories, SHA512PasswordEncryptor())
        return when (scenario.lowercase()) {
            "default" -> DefaultScenario(scenarioBuilder).run()
            else -> throw Throwable("Scenario $scenario not found")
        }
    }
}
