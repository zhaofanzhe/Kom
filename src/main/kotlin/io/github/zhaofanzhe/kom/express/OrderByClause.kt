package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class OrderByClause(vararg exprs: SortExpress) : ClauseExpressBuilder() {

    private val orderBy = "\norder by "

    init {
        if (exprs.isNotEmpty()) {
            expressBuilder.append(orderBy)
            expressBuilder.append(ExpressMerge(*exprs, separator = ", ").express())
        }
    }
}