package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult

class LimitClause(private val size: Long) : Clause() {

    private val limit = "\nlimit ?"

    override fun generate(context: Context, result: ExpressResult) {
        if (size > 0) {
            result.append(limit, size)
        }
    }

}