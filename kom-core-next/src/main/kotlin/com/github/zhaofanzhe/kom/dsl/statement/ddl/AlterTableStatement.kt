package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.Bundle

class AlterTableStatement(
    val executor: Executor,
    val table: Table,
) : Statement {

    override fun generateStatement(): Bundle {
        return Bundle("alter table `${this.table.name}`")
    }

}

fun AlterTableStatement.addColumn(column: Column<*>): AlterTableAddColumnStatement {
    return AlterTableAddColumnStatement(executor, this, column)
}

fun AlterTableStatement.addColumn(fn: Table.() -> Column<*>): AlterTableAddColumnStatement {
    return addColumn(table.fn())
}

fun AlterTableStatement.modifyColumn(column: Column<*>): AlterTableModifyColumnStatement {
    return AlterTableModifyColumnStatement(executor, this, column)
}

fun AlterTableStatement.modifyColumn(fn: Table.() -> Column<*>): AlterTableModifyColumnStatement {
    return modifyColumn(table.fn())
}

fun AlterTableStatement.dropColumn(column: Column<*>): AlterTableDropColumnStatement {
    return AlterTableDropColumnStatement(executor, this, column)
}

fun AlterTableStatement.dropColumn(fn: Table.() -> Column<*>): AlterTableDropColumnStatement {
    return dropColumn(table.fn())
}