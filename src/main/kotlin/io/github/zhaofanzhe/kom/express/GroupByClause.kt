package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class GroupByClause(private vararg val columns: Column<*>) : ClauseExpressBuilder() {

    private val groupBy = "\ngroup by "

    override fun generate(context: Context) {
        super.generate(context)
        if (columns.isNotEmpty()) {
            expressBuilder.append(groupBy)
            expressBuilder.append(columns.joinToString(separator = ", ") { it.columnName() })
        }
    }

}