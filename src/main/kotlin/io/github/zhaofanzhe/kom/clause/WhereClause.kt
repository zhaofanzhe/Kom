package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.*

class WhereClause(
    private val express: LogicExpress<Boolean>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        println(express.notNullAndHasLogic())
        if (express.notNullAndHasLogic()){
            result += "\nwhere "
            result += express.generate(context)
        }
        return result
    }

}