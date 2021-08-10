package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

class SelectClause(
    private val exprs: List<DeclareExpress>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        result += "select "
        exprs.forEachIndexed { index, express ->
            if (index > 0){
                result += ", "
            }
            express.generate(context,result)
        }
    }

}