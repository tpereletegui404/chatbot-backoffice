package com.proyecto404.backoffice.base.testing

import org.assertj.core.api.ObjectAssert

@Suppress("DEPRECATION")
fun <T> ObjectAssert<T>.isFieldByFieldEqualTo(actual: T): ObjectAssert<T> = isEqualToComparingFieldByField(actual)
