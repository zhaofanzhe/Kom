package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class SelectClause(
    private val exprs: List<DeclareExpress>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += "select "
        result.append(separator = ", ",exprs.map { it.generate(context) })
        return result
    }

}