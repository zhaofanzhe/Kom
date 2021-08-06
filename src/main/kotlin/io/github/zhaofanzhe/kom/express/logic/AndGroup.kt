package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.LogicExpress


@Suppress("DuplicatedCode")
class AndGroup : LogicExpress() {

    fun and(other: LogicExpress) {
        val layer = logicLayer
        val otherLayer = other.logicLayer
        if (layer > 0) {
            expressBuilder.append(" and ")
        }
        if (otherLayer >= layer) {
            logicLayer = otherLayer + 1
        }
        if (otherLayer > 0) {
            expressBuilder.append("(")
        }
        expressBuilder.append(other.express())
        if (otherLayer > 0) {
            expressBuilder.append(")")
        }
        paramsBuilder.addAll(other.params())
    }

    fun or(init: OrGroup.() -> Unit) {
        val scope = OrGroup()
        scope.init()
        and(scope)
    }

}