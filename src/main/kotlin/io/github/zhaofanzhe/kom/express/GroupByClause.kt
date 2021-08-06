package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class GroupByClause(vararg columns: Column<*>) : ClauseExpressBuilder() {

    private val groupBy = "\ngroup by "

    init {
        if (columns.isNotEmpty()) {
            expressBuilder.append(groupBy)
            expressBuilder.append(columns.joinToString(separator = ", ") { it.columnName() })
        }
    }
}