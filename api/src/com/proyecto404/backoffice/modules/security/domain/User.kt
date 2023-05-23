package com.proyecto404.backoffice.modules.security.domain

import com.proyecto404.backoffice.modules.common.base.domain.Id
import com.proyecto404.backoffice.modules.security.app.authorization.Roles
import com.proyecto404.backoffice.modules.security.domain.passwords.PasswordEncryptor

class User(
    val id: Id<User>,
    val username: String,
    encryptedPassword: String,
    val salt: String,
    roles: Set<Roles>,
) {
    var roles: MutableSet<Roles> = roles.toMutableSet()
        set(value) {
            if (value.any { !Roles.userAssignableRoles().contains(it) }) {
                throw IllegalArgumentException("Illegal roles")
            }
            field = value
        }
    var encryptedPassword = encryptedPassword
        private set
    var sessionToken: String? = null

    fun isValidPassword(passwordEncryptor: PasswordEncryptor, password: String): Boolean {
        val encryptedOtherPassword = passwordEncryptor.encrypt(password, salt)
        return encryptedOtherPassword == encryptedPassword
    }

    override fun equals(other: Any?) = other is User && other.id == id

    override fun hashCode() = id.hashCode()

    override fun toString() = "User($id, '$username')"
}
