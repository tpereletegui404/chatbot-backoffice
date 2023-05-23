package com.proyecto404.backoffice.base.integration.application

import com.proyecto404.backoffice.base.domain.Id

data class CreateResponse<T>(val id: Id<T>)
