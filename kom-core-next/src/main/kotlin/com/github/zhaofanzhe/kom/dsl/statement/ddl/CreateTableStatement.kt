package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.core.execute
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.Bundle

class CreateTableStatement(
    val executor: Executor,
    val table: Table,
) : Statement {

    override fun generateStatement(): Bundle {
        var sql = "create table `${this.table.name}` (\r\n"
        val list = mutableListOf<String>()
        for (column in this.table.columns) {
            list += ColumnStatement.generate(column)
        }
        list += "primary key (${this.table.primaryKey.joinToString(", ") { "`${it.name}`" }})"
        for (index in this.table.indexes) {
            list += "index ${this.table.name}_${index.key}_index (${index.value.joinToString(separator = ", ") { "`${it.name}`" }})"
        }
        for (index in this.table.uniqueIndexes) {
            list += "unique index ${this.table.name}_${index.key}_uindex (${index.value.joinToString(separator = ", ") { "`${it.name}`" }})"
        }
        sql += list.joinToString(separator = ", \r\n") { "    $it" }
        sql += "\r\n)"
        return Bundle(sql = sql)
    }

}

fun CreateTableStatement.execute(): Int {
    return this.executor.execute(generateStatement())
}