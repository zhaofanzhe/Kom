package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult

@Suppress("PrivatePropertyName")
class OrderByClause(private vararg val exprs: SortExpress) : Clause() {

    private val ORDER_BY = "\norder by "

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        if (exprs.isNotEmpty()) {
            result.append(ORDER_BY)
            result.append(separator = ", ", results = exprs.map { it.generate(context) })
        }
        return result
    }

}