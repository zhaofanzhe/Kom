package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult

@Suppress("PrivatePropertyName")
class OffsetClause(private val size: Long) : Clause() {

    private val OFFSET = " offset ?"

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        if (size > 0) {
            result.append(OFFSET, size)
        }
        return result
    }

}