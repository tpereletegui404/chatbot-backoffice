package com.proyecto404.backoffice.base.domain.errors

class RequiredArgumentError(val name: String, message: String? = null): DomainError(message ?: "Argument $name is required")
