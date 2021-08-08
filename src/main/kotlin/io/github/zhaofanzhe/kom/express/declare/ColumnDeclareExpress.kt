package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context

/**
 * 声明表达式
 */
class ColumnDeclareExpress(private val column: Column<*>) : DeclareExpress() {

    override fun generate(context: Context) {
        val tableAlias = context.tableAliasGenerator.next(column.table())
        val columnName = column.columnName()
        val columnAlias = context.columnAliasGenerator.next(column)

        expressBuilder.append(tableAlias)
        expressBuilder.append('.')
        expressBuilder.append(columnName)
        expressBuilder.append(" as ")
        expressBuilder.append(columnAlias)
    }

}