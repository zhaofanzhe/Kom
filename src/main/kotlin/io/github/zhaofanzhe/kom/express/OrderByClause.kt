package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

class OrderByClause(private vararg val exprs: SortExpress) : ClauseExpressBuilder() {

    private val orderBy = "\norder by "

    override fun generate() {
        super.generate()
        if (exprs.isNotEmpty()) {
            expressBuilder.append(orderBy)
            val merge = ExpressMerge(*exprs, separator = ", ")
            merge.generate()
            expressBuilder.append(merge.express())
        }
    }

}