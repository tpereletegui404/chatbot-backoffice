package com.proyecto404.backoffice.modules.common.domain

import com.proyecto404.backoffice.modules.common.base.testing.TestExamples

object GeneralExamples {
    val phones = TestExamples("011-666-5555", "7778900", "15-6-555-0000")
    val firstNames = TestExamples("Alice", "Bob", "Charly", "David", "Eric")
    val lastNames = TestExamples("Brown", "Johnson", "McCartney", "Borg", "Davies")
    val fullNames = TestExamples(List(10) { firstNames.one() + " " + lastNames.one() })
    val addresses = TestExamples("Cabildo 123", "Maipu 222, Olivos, Buenos Aires", "Ecuador 472", "Rivadavia 45 2doB")
    val emails = TestExamples(List(50) { "${firstNames.one().lowercase()}.$it@example.com" })
    val countryCodes = TestExamples("AR", "UY", "US", "CL", "BR")
}
