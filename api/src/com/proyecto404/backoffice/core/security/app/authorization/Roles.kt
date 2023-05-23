package com.proyecto404.backoffice.core.security.app.authorization

enum class Roles {
    Member,
    Administrative,
    ProductOwner,
    Admin,
    System;

    companion object {
        fun userAssignableRoles() = values().filter { it != System }
    }
}
