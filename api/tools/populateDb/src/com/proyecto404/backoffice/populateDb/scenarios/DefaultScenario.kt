package com.proyecto404.backoffice.populateDb.scenarios


import com.proyecto404.backoffice.core.common.domain.ScenarioBuilder

class DefaultScenario(private val scenario: ScenarioBuilder) {
    fun run() {
        val alice = scenario.alice
    }
}
