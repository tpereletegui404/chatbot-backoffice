package com.proyecto404.backoffice.modules.common.base.domain.errors

import com.proyecto404.backoffice.modules.common.base.domain.errors.DomainError

open class NotFoundError(message: String = "Not found"): DomainError(message)
