package com.github.zhaofanzhe.kom.dsl.statement.dml

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.executeCreate
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table

class InsertStatement(
    internal val executor: Executor,
    internal val table: Table,
) : Statement {

    internal val defaultValues = mutableMapOf<Column<*>, Any?>()

    internal val values = mutableMapOf<Column<*>, Any?>()

    init {
        table.columns.filter { it.default != null }.forEach {
            defaultValues[it] = it.default?.invoke()
        }
        values.putAll(defaultValues)
    }

    override fun generateStatement(): Bundle {
        var sql = "insert into `${table.name}` "
        val args = mutableListOf<Any?>()

        sql += "(${values.map { it.key.generateSelectable().sql }.joinToString(", ")}) "
        sql += "values (${values.map { '?' }.joinToString(", ")})"
        args.addAll(values.map { it.value })

        return Bundle(
            sql = sql,
            args = args,
        )
    }

}

fun InsertStatement.set(key: Column<*>, value: Any?): InsertStatement {
    this.values += key to value
    return this
}

fun InsertStatement.execute(): Long? {
    return executor.executeCreate(generateStatement())
}