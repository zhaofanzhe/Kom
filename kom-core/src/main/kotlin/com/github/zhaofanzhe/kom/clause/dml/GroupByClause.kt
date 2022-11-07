package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.*

@Suppress("PrivatePropertyName")
class GroupByClause(private vararg val columns: Column<*, *>) : Clause() {

    private val GROUP_BY = "\ngroup by "

    override fun generate(context: Context, result: ExpressResult) {
        if (columns.isNotEmpty()) {
            result += GROUP_BY
            columns.forEach { it.express().generate(context, result) }
        }
    }

}