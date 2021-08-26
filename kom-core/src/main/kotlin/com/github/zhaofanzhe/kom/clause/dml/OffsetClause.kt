package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult

@Suppress("PrivatePropertyName")
class OffsetClause(private val size: Long) : Clause() {

    private val OFFSET = " offset "

    override fun generate(context: Context, result: ExpressResult) {
        if (size > 0) {
            result.append(OFFSET)
            result.append("?", size)
        }
    }

}