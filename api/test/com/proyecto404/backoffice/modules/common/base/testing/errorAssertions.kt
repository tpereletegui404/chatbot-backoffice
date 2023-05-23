package com.proyecto404.backoffice.modules.common.base.testing

import com.proyecto404.backoffice.modules.common.base.domain.errors.ArgumentCannotBeEmptyError
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.assertThrows

fun assertThrowsArgumentCannotBeEmpty(argumentName: String, executable: () -> Unit) {
    val error = assertThrows<ArgumentCannotBeEmptyError>(executable)
    Assertions.assertThat(error.name).isEqualTo(argumentName)
}
