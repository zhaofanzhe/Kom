package com.github.zhaofanzhe.kom.dsl.statement.dml

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.clause.WhereClause
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

class UpdateStatement(
    internal val executor: Executor,
    internal val table: Table,
) : Statement {

    internal val updates = mutableMapOf<Column<*>, Any?>()

    internal var where: WhereClause? = null

    override fun generateStatement(): Bundle {
        var sql = "update ${table.tableDefine()}\r\n"
        val args = mutableListOf<Any?>()

        sql += "set ${updates.map { "${it.key.generateSelectable().sql} = ?" }.joinToString(", ")}\r\n"
        args.addAll(updates.map { it.value })

        if (where != null) {
            val bundle = where!!.generateClause()
            sql += bundle.sql
            args.addAll(bundle.args)
        }

        return Bundle(
            sql = sql,
            args = args,
        )
    }

}

fun UpdateStatement.set(key: Column<*>, value: Any?): UpdateStatement {
    this.updates += key to value
    return this
}

fun UpdateStatement.where(clause: WhereClause): UpdateStatement {
    this.where = clause
    return this
}

fun UpdateStatement.where(express: Express<Boolean>): UpdateStatement {
    return where(WhereClause(express))
}

fun UpdateStatement.where(fn: () -> Express<Boolean>): UpdateStatement {
    return where(fn())
}

fun UpdateStatement.execute(): Int {
    return executor.execute(generateStatement())
}