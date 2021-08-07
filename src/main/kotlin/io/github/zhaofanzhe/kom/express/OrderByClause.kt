package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ClauseExpressBuilder

@Suppress("PrivatePropertyName")
class OrderByClause(private vararg val exprs: SortExpress) : ClauseExpressBuilder() {

    private val ORDER_BY = "\norder by "

    override fun generate(context: Context) {
        super.generate(context)
        if (exprs.isNotEmpty()) {
            expressBuilder.append(ORDER_BY)
            val merge = ExpressMerge(*exprs, separator = ", ")
            merge.generate(context)
            expressBuilder.append(merge.express())
        }
    }

}