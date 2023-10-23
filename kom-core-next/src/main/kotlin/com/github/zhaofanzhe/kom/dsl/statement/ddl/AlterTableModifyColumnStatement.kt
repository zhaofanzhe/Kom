package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

class AlterTableModifyColumnStatement(
    internal val executor: Executor,
    internal val statement: AlterTableStatement,
    internal val column: Column<*>,
) : Statement {

    override fun generateStatement(): Bundle {
        val bundle = statement.generateStatement()
        return Bundle("${bundle.sql} modify column ${ColumnStatement.generate(column)}")
    }

}

fun AlterTableModifyColumnStatement.execute(): Int {
    return this.executor.execute(generateStatement())
}