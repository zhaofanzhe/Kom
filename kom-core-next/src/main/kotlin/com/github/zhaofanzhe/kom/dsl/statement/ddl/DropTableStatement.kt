package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.Bundle

class DropTableStatement(
    val executor: Executor,
    val table: Table,
) : Statement {

    override fun generateStatement(): Bundle {
        return Bundle("drop table `${this.table.name}`")
    }

}

fun DropTableStatement.execute(): Int {
    return this.executor.execute(generateStatement())
}