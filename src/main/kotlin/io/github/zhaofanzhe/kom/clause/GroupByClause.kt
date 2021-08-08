package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.*

@Suppress("PrivatePropertyName")
class GroupByClause(private vararg val columns: Column<*>) : Clause() {

    private val GROUP_BY = "\ngroup by "

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        if (columns.isNotEmpty()) {
            result += GROUP_BY
            result += columns.map { it.express().generate(context) }
        }
        return result
    }

}