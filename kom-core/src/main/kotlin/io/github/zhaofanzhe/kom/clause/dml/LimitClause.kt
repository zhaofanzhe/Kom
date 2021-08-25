package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult

class LimitClause(private val size: Long) : Clause() {

    private val limit = "\nlimit "

    override fun generate(context: Context, result: ExpressResult) {
        if (size > 0) {
            result.append(limit)
            result.append("?", size)
        }
    }

}