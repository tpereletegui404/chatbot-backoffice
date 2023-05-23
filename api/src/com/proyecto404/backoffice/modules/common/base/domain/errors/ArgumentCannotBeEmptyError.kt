package com.proyecto404.backoffice.modules.common.base.domain.errors

class ArgumentCannotBeEmptyError(val name: String, message: String? = null): DomainError(message ?: "Argument $name cannot be empty")
