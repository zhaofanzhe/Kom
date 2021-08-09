package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult
import io.github.zhaofanzhe.kom.express.LogicExpress


@Suppress("DuplicatedCode")
class AndGroup : LogicExpress<Boolean>() {

    private val exprs = mutableListOf<LogicExpress<*>>()

    fun and(other: LogicExpress<Boolean>) {
        exprs += other
    }

    fun or(init: OrGroup.() -> Unit) {
        val scope = OrGroup()
        scope.init()
        and(scope)
    }

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        exprs.forEachIndexed { index, express ->
            val rst = express.generate(context)

            val layer = logicLayer
            val otherLayer = express.logicLayer
            if (index > 0) {
                result += " and "
            }
            if (otherLayer >= layer) {
                logicLayer = otherLayer + 1
            }
            if (otherLayer > 0) {
                result += "("
            }
            result += rst
            if (otherLayer > 0) {
                result += ")"
            }
        }
        return result
    }

    override fun hasLogic(): Boolean {
        return exprs.isNotEmpty()
    }

}