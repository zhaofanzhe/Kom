package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.statement.dml.QueryStatement
import com.github.zhaofanzhe.kom.dsl.statement.dml.UpdateStatement
import com.github.zhaofanzhe.kom.dsl.statement.dml.select
import com.github.zhaofanzhe.kom.dsl.table.Table

class Database(
    internal val factory: ConnectionFactory,
) {

    internal val executor: Executor = Executor(factory)

}

fun Database.select(vararg selectables: Selectable): QueryStatement {
    return QueryStatement(executor).apply { select(*selectables) }
}

fun Database.update(table: Table): UpdateStatement {
    return UpdateStatement(executor, table)
}