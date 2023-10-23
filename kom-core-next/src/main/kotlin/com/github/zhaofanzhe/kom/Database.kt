package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.statement.ddl.AlterTableStatement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.CreateTableStatement
import com.github.zhaofanzhe.kom.dsl.statement.dml.*
import com.github.zhaofanzhe.kom.dsl.table.Table

class Database(
    internal val factory: ConnectionFactory,
) {

    internal val executor: Executor = Executor(factory)

}

fun Database.insert(table: Table): InsertStatement {
    return InsertStatement(executor, table)
}

fun Database.update(table: Table): UpdateStatement {
    return UpdateStatement(executor, table)
}

fun Database.delete(table: Table): DeleteStatement {
    return DeleteStatement(executor, table)
}

fun Database.select(vararg selectables: Selectable): QueryStatement {
    return QueryStatement(executor).apply { select(*selectables) }
}

fun Database.createTable(table: Table): CreateTableStatement {
    return CreateTableStatement(executor, table)
}

fun Database.alterTable(table: Table): AlterTableStatement {
    return AlterTableStatement(executor, table)
}