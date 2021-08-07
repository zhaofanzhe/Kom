package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

class ColumnExpress(private val column: Column<*>):ExpressBuilder() {

    override fun generate(context: Context) {
        super.generate(context)

        val tableAlias = context.tableAliasGenerator.next(column.table())
        val columnName = column.columnName()

        expressBuilder.append(tableAlias)
        expressBuilder.append('.')
        expressBuilder.append(columnName)
    }

}