package com.github.zhaofanzhe.kom.dsl.statement.ddl.index

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.AlterTableStatement

class AddIndexStatement(
    internal val executor: Executor,
    internal val statement: AlterTableStatement,
    internal val indexName: String,
    internal val unique: Boolean,
    internal val columns: List<Column<*>>,
) : Statement {

    override fun generateStatement(): Bundle {
        val bundle = statement.generateStatement()
        val list = mutableListOf(bundle.sql, "add")
        if (unique) list += "unique"
        list += "index"
        list += indexName
        list += "(${columns.joinToString(separator = ", ") { it.name }})"
        return Bundle(list.joinToString(separator = " "))
    }

}

fun AddIndexStatement.execute(): Int {
    return this.executor.execute(generateStatement())
}