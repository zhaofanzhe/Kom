package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.*

class CompareLogicExpress(
    private val operator: String,
    private val left: Any?,
    private val right: Any?,
) : LogicExpress<Boolean>() {

    @Suppress("DuplicatedCode")
    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        if (left is Express) {
            result += left.generate(context)
        } else {
            result.append("?",left)
        }
        result += """ $operator """
        if (right is Express) {
            result += right.generate(context)
        } else {
            result.append("?",right)
        }
        return result
    }

    override fun hasLogic(): Boolean {
        return true
    }

}