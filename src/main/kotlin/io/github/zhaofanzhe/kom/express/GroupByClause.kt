package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class GroupByClause(vararg fields: Field<*>) : ClauseExpressBuilder() {

    private val groupBy = "\ngroup by "

    init {
        if (fields.isNotEmpty()) {
            expressBuilder.append(groupBy)
            expressBuilder.append(fields.joinToString(separator = ", ") { it.columnName() })
        }
    }
}