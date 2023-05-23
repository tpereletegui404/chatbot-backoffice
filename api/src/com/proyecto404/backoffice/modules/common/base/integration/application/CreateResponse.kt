package com.proyecto404.backoffice.modules.common.base.integration.application

import com.proyecto404.backoffice.modules.common.base.domain.Id

data class CreateResponse<T>(val id: Id<T>)
