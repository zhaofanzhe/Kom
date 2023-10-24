package com.github.zhaofanzhe.kom.dsl.statement.ddl.index

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.AlterTableStatement

class DropIndexStatement(
    internal val executor: Executor,
    internal val statement: AlterTableStatement,
    internal val indexName: String,
) : Statement {

    override fun generateStatement(): Bundle {
        val bundle = statement.generateStatement()
        return Bundle("${bundle.sql} drop index $indexName")
    }

}

fun DropIndexStatement.execute(): Int {
    return this.executor.execute(generateStatement())
}