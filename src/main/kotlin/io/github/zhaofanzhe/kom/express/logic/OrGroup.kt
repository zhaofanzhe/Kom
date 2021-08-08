package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.LogicExpress


@Suppress("DuplicatedCode", "MemberVisibilityCanBePrivate")
class OrGroup : LogicExpress<Boolean>() {

    private val exprs = mutableListOf<LogicExpress<*>>()

    fun or(other: LogicExpress<Boolean>) {
        exprs += other
    }

    fun and(init: AndGroup.() -> Unit) {
        val scope = AndGroup()
        scope.init()
        or(scope)
    }

    override fun generate(context: Context) {
        super.generate(context)

        exprs.forEach {
            it.generate(context)

            val layer = logicLayer
            val otherLayer = it.logicLayer
            if (expressBuilder.isNotEmpty()) {
                expressBuilder.append(" or ")
            }
            if (otherLayer >= layer) {
                logicLayer = otherLayer + 1
            }
            if (otherLayer > 0) {
                expressBuilder.append("(")
            }
            expressBuilder.append(it.express())
            if (otherLayer > 0) {
                expressBuilder.append(")")
            }
            paramsBuilder.addAll(it.params())
        }

    }

}