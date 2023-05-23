package com.proyecto404.backoffice.modules.common.base.domain

class Id<T>(private val rawId: Int): Comparable<Id<T>> {
    override fun equals(other: Any?) = other is Id<*> && other.javaClass == this.javaClass && other.rawId == rawId

    override fun hashCode() = rawId

    override fun toString() = this.javaClass.simpleName + "($rawId)"

    fun toInt() = rawId

    override fun compareTo(other: Id<T>) = rawId.compareTo(other.rawId)
}
