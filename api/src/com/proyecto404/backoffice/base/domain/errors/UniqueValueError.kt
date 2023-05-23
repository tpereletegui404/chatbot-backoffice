package com.proyecto404.backoffice.base.domain.errors

class UniqueValueError(val name: String, message: String? = null): DomainError(message ?: "$name must be unique")
