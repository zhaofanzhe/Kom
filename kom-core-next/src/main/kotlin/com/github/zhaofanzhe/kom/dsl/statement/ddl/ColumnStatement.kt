package com.github.zhaofanzhe.kom.dsl.statement.ddl

import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.Statement
import com.github.zhaofanzhe.kom.dsl.Bundle

class ColumnStatement(
    val column: Column<*>,
) : Statement {

    override fun generateStatement(): Bundle {
        return Bundle(generate(column))
    }

    companion object {

        fun generate(column: Column<*>): String {
            var text = "`${column.name}` ${column.type}"
            text += if (column.isNullable) {
                " null"
            } else {
                " not null"
            }
            if (column.isUnique) {
                text += " unique"
            }
            if (column.isAutoIncrement) {
                text += " auto_increment"
            }
            if (column.comment != null) {
                text += " comment '${column.comment}'"
            }
            return text
        }

    }

}
