package com.proyecto404.backoffice.modules.common.base.domain.errors

import com.proyecto404.backoffice.modules.common.base.domain.errors.DomainError

class UniqueValueError(val name: String, message: String? = null): DomainError(message ?: "$name must be unique")
