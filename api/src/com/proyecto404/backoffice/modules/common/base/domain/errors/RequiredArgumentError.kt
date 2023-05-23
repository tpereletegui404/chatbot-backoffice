package com.proyecto404.backoffice.modules.common.base.domain.errors

import com.proyecto404.backoffice.modules.common.base.domain.errors.DomainError

class RequiredArgumentError(val name: String, message: String? = null): DomainError(message ?: "Argument $name is required")
