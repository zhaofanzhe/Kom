package com.github.zhaofanzhe.kom.express.declare

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult

/**
 * 字段定义表达式
 */
class DeclareStatementExpress<T : Any?>(
    private val column: Declare<T>,
) : DeclareExpress() {

    override fun generate(context: Context, result: ExpressResult) {
        result.append(context.currentTableAlias(column.table))
        result.append(".")
        result.append(context.currentDeclareName(column))
        result.append(" as ")
        result.append(context.currentDeclareAlias(column))
    }

}