package com.github.zhaofanzhe.kom.dsl.statement.ddl.column

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.AlterTableStatement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.ColumnStatement

class AddColumnStatement(
    internal val executor: Executor,
    internal val statement: AlterTableStatement,
    internal val column: Column<*>,
) : Statement {

    override fun generateStatement(): Bundle {
        val bundle = statement.generateStatement()
        return Bundle("${bundle.sql} add column ${ColumnStatement.generate(column)}")
    }

}

fun AddColumnStatement.execute(): Int {
    return this.executor.execute(generateStatement())
}