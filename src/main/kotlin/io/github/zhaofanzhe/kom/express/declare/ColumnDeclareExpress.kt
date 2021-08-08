package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context

/**
 * 声明表达式
 */
class ColumnDeclareExpress(private val column: Column<*>) : DeclareExpress() {

    override fun generate(context: Context) {
        super.generate(context)

        val currentTable = context.tables[column.table().rootTable()] ?: throw KomException("not fund currentTable.")

        val tableAlias = context.tableAliasGenerator.next(currentTable)
        val columnName = column.columnName()
        val columnAlias = context.columnAliasGenerator.next(column)

        val prototype = column.prototype() as? Column<*>

        expressBuilder.append(tableAlias)
        expressBuilder.append('.')

        if (prototype == null) {
            expressBuilder.append(columnName)
        } else {
            expressBuilder.append(context.columnAliasGenerator.next(prototype))
        }

        expressBuilder.append(" as ")
        expressBuilder.append(columnAlias)
    }

}