package com.proyecto404.backoffice.base.lang

inline fun <T, reified R> Collection<T>.toTypedArray(transform: (T) -> R) = map(transform).toTypedArray()
