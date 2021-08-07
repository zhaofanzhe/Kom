package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.Context
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

    override fun generate(context: Context) {
        super.generate(context)

        exprs.forEach {
            it.generate(context)

            val layer = logicLayer
            val otherLayer = it.logicLayer
            if (layer > 0) {
                expressBuilder.append(" and ")
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