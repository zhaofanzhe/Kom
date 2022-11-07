package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult

@Suppress("PrivatePropertyName")
class OrderByClause(private vararg val exprs: SortExpress) : Clause() {

    private val ORDER_BY = "\norder by "

    override fun generate(context: Context, result: ExpressResult) {
        if (exprs.isNotEmpty()) {
            result.append(ORDER_BY)
            exprs.forEachIndexed { index, express ->
                if (index > 0) {
                    result += ", "
                }
                express.generate(context, result)
            }
        }
    }

}