package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult

class LimitClause(private val size: Long) : Clause() {

    private val limit = "\nlimit "

    override fun generate(context: Context, result: ExpressResult) {
        if (size > 0) {
            result.append(limit)
            result.append("?", size)
        }
    }

}