package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.declare.DeclareExpress

class SelectClause(
    private val exprs: List<DeclareExpress>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        result += "select "
        exprs.forEachIndexed { index, express ->
            if (index > 0) {
                result += ", "
            }
            express.generate(context, result)
        }
    }

}