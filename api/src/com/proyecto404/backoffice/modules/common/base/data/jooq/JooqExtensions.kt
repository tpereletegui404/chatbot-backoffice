package com.proyecto404.backoffice.modules.common.base.data.jooq

import org.jooq.*
import org.jooq.impl.DSL

fun <R: Record> DSLContext.insert(table: Table<R>, applyChanges: R.() -> Unit): InsertValuesStepN<R> {
    val record = newRecord(table)
    record.applyChanges()
    val values = record.fields().map { record.get(it) }
    return insertInto(table, *record.fields()).values(values)
}

fun <R: Record> DSLContext.executeInsert(table: Table<R>, applyChanges: R.() -> Unit): Int {
    return insert(table, applyChanges).execute()
}

@Suppress("UNCHECKED_CAST")
fun <R: Record> DSLContext.update(table: Table<R>, applyChanges: R.() -> Unit): Update<R> {
    val record = newRecord(table)
    record.applyChanges()
    val primaryKeyFields = table.primaryKey?.fields ?: listOf()
    val valueFields = record.fields().filter { !primaryKeyFields.contains(it) && it.changed(record)}
    val setMap = valueFields.associateWith { record.get(it) }
    val updateClause = update(table).set(setMap)
    if (primaryKeyFields.isEmpty()) return updateClause

    val whereConditions = primaryKeyFields.map { (it as Field<Any>).eq(record.get(it)) }
    return updateClause.where(*whereConditions.toTypedArray())
}

fun <R: Record> DSLContext.executeUpdate(table: Table<R>, applyChanges: R.() -> Unit): Int {
    return update(table, applyChanges).execute()
}

fun DSLContext.executeBatch(queries: List<Query>): IntArray {
    if (queries.isEmpty()) return IntArray(0)
    return batch(queries).execute()
}

inline fun <T> T?.toCondition(block: (T) -> Condition): Condition {
    return this?.let(block) ?: DSL.noCondition()
}
