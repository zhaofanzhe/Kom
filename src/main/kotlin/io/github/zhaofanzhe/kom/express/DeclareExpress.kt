package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

/**
 * 声明表达式
 */
class DeclareExpress(private val column: Column<*>) : ExpressBuilder() {

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