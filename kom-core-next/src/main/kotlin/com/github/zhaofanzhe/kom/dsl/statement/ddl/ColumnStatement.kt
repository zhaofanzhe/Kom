package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement

class ColumnStatement(
    val column: Column<*>,
) : Statement {

    override fun generateStatement(): Bundle {
        return Bundle(generate(column))
    }

    companion object {

        fun generate(column: Column<*>): String {
            val list = mutableListOf(column.name, column.type)
            list += if (column.isNullable) {
                "null"
            } else {
                "not null"
            }
            if (column.isAutoIncrement) {
                list += "auto_increment"
            }
            if (column.comment != null) {
                list += "comment '${column.comment}'"
            }
            return list.joinToString(separator = " ")
        }

    }

}
