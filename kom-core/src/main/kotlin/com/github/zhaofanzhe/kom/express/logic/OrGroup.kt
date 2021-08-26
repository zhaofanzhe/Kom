package com.github.zhaofanzhe.kom.express.logic

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.LogicExpress


@Suppress("DuplicatedCode", "MemberVisibilityCanBePrivate")
class OrGroup : LogicExpress<Boolean>() {

    private val exprs = mutableListOf<LogicExpress<*>>()

    operator fun plusAssign(other: LogicExpress<Boolean>){
        or(other)
    }

    fun or(other: LogicExpress<Boolean>) {
        exprs += other
    }

    fun and(init: AndGroup.() -> Unit) {
        val scope = AndGroup()
        scope.init()
        or(scope)
    }

    override fun generate(context: Context, result: ExpressResult) {
        exprs.forEachIndexed { index, express ->
            val layer = logicLayer
            val otherLayer = express.logicLayer
            if (index > 0) {
                result += " or "
            }
            if (otherLayer >= layer) {
                logicLayer = otherLayer + 1
            }
            if (otherLayer > 0) {
                result += "("
            }
            express.generate(context,result)
            if (otherLayer > 0) {
                result += ")"
            }
        }
    }

    override fun hasLogic(): Boolean {
        return exprs.isNotEmpty()
    }

}