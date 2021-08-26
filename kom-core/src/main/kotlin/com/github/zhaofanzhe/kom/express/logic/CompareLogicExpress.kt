package com.github.zhaofanzhe.kom.express.logic

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.Express
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.LogicExpress

class CompareLogicExpress(
    private val operator: String,
    private val left: Any?,
    private val right: Any?,
) : LogicExpress<Boolean>() {

    @Suppress("DuplicatedCode")
    override fun generate(context: Context, result: ExpressResult) {
        if (left is Express) {
            left.generate(context, result)
        } else {
            result.append("?", left)
        }
        result += """ $operator """
        if (right is Express) {
            right.generate(context, result)
        } else {
            result.append("?", right)
        }
    }

    override fun hasLogic(): Boolean {
        return true
    }

}