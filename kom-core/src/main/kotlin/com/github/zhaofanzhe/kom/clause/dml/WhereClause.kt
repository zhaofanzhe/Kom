package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.LogicExpress

class WhereClause(
    private val express: LogicExpress<Boolean>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        if (express.hasLogic()) {
            result += "\nwhere "
            express.generate(context, result)
        }
    }

}