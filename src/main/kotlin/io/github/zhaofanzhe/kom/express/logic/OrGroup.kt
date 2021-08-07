package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.LogicExpress


@Suppress("DuplicatedCode")
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

    override fun generate() {
        super.generate()

        exprs.forEach {
            it.generate()

            val layer = logicLayer
            val otherLayer = it.logicLayer
            if (layer > 0) {
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