package com.github.zhaofanzhe.kom.dsl.statement.dml

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.Bundle

class InsertStatement(
    internal val executor: Executor,
    internal val table: Table,
) : Statement {

    internal val updates = mutableMapOf<Column<*>, Any?>()

    override fun generateStatement(): Bundle {
        var sql = "insert into `${table.name}` "
        val args = mutableListOf<Any?>()

        sql += "(${updates.map { it.key.generateSelectable().sql }.joinToString(", ")}) "
        sql += "values (${updates.map { '?' }.joinToString(", ")})"
        args.addAll(updates.map { it.value })

        return Bundle(
            sql = sql,
            args = args,
        )
    }

}

fun InsertStatement.set(key: Column<*>, value: Any?): InsertStatement {
    this.updates += key to value
    return this
}

fun InsertStatement.execute(): Int {
    return executor.execute(generateStatement())
}