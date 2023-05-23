package com.proyecto404.backoffice.core.security.app.authorization

@Target(AnnotationTarget.CLASS)
annotation class Authorization(val roles: Array<Roles> = [])

fun authorizedRoles(clazz: Class<*>): Array<Roles> {
    val authorization = clazz.annotations.firstOrNull { it is Authorization } as? Authorization
    return authorization?.roles ?: arrayOf()
}
