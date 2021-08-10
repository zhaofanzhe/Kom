package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.*

class WhereClause(
    private val express: LogicExpress<Boolean>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        println(express.notNullAndHasLogic())
        if (express.notNullAndHasLogic()){
            result += "\nwhere "
            express.generate(context,result)
        }
    }

}