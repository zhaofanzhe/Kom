package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.ColumnDeclareExpress
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

data class Column<T>(
    private val table: Table<*>,
    private val columnName: String,
    private val fieldName: String,
) : Declare<T> {

    internal fun columnName(): String {
        return columnName
    }

    internal fun fieldName(): String {
        return fieldName
    }

    internal fun table(): Table<*> {
        return table
    }

    internal fun columnExpress(): ColumnExpress {
        return ColumnExpress(this)
    }

    override fun declare(): DeclareExpress {
        return ColumnDeclareExpress(this)
    }

    override fun toString(): String {
        return """${table.tableName()}.${columnName}"""
    }

}