package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.core.Executor
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

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
            list += "index ${this.table.name}_${index.key} (${index.value.joinToString(separator = ", ") { "`${it.name}`" }})"
        }
        sql += list.joinToString(separator = ", \r\n") { "    $it" }
        sql += "\r\n)"
        return Bundle(sql = sql)
    }

}