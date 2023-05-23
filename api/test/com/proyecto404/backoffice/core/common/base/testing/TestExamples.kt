package com.proyecto404.backoffice.base.testing

class TestExamples<T>(examples: List<T>) {
    private var examples = ThreadLocal.withInitial { examples }
    private var current = -1

    constructor(vararg examples: T): this(examples.toList())

    fun one(): T {
        current++
        return examples.get()[current % examples.get().size]
    }
}
