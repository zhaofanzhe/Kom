package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.column.AddColumnStatement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.column.DropColumnStatement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.column.ModifyColumnStatement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.index.AddIndexStatement
import com.github.zhaofanzhe.kom.dsl.statement.ddl.index.DropIndexStatement
import com.github.zhaofanzhe.kom.dsl.table.Table

class AlterTableStatement(
    val executor: Executor,
    val table: Table,
) : Statement {

    override fun generateStatement(): Bundle {
        return Bundle("alter table `${this.table.name}`")
    }

}

fun AlterTableStatement.addColumn(column: Column<*>): AddColumnStatement {
    return AddColumnStatement(executor, this, column)
}

fun AlterTableStatement.addColumn(fn: Table.() -> Column<*>): AddColumnStatement {
    return addColumn(table.fn())
}

fun AlterTableStatement.modifyColumn(column: Column<*>): ModifyColumnStatement {
    return ModifyColumnStatement(executor, this, column)
}

fun AlterTableStatement.modifyColumn(fn: Table.() -> Column<*>): ModifyColumnStatement {
    return modifyColumn(table.fn())
}

fun AlterTableStatement.dropColumn(column: Column<*>): DropColumnStatement {
    return DropColumnStatement(executor, this, column)
}

fun AlterTableStatement.dropColumn(fn: Table.() -> Column<*>): DropColumnStatement {
    return dropColumn(table.fn())
}

fun AlterTableStatement.addIndex(
    indexName: String,
    unique: Boolean,
    columns: List<Column<*>>,
): AddIndexStatement {
    return AddIndexStatement(executor, this, indexName, unique, columns)
}

fun AlterTableStatement.dropIndex(indexName: String): DropIndexStatement {
    return DropIndexStatement(executor, this, indexName)
}