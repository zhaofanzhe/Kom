package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult

@Suppress("PrivatePropertyName")
class OffsetClause(private val size: Long) : Clause() {

    private val OFFSET = " offset ?"

    override fun generate(context: Context, result: ExpressResult) {
        if (size > 0) {
            result.append(OFFSET, size)
        }
    }

}