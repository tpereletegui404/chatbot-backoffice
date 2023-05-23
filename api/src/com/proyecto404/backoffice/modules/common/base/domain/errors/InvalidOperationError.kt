package com.proyecto404.backoffice.modules.common.base.domain.errors

import com.proyecto404.backoffice.modules.common.base.domain.errors.DomainError

class InvalidOperationError(message: String = ""): DomainError(message)
