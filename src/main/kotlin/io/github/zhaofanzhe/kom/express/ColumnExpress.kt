package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

class ColumnExpress(private val column: Column<*>):ExpressBuilder() {

    override fun generate(context: Context) {
        super.generate(context)

        val currentTable = context.tables[column.table().rootTable()] ?: throw KomException("not fund currentTable.")

        val tableAlias = context.tableAliasGenerator.next(currentTable)
        val columnName = column.columnName()

        expressBuilder.append(tableAlias)
        expressBuilder.append('.')
        expressBuilder.append(columnName)
    }

}