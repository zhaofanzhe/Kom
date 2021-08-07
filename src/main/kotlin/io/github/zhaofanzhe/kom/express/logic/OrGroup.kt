package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.LogicExpress


@Suppress("DuplicatedCode")
class OrGroup : LogicExpress<Boolean>() {

    fun or(other: LogicExpress<Boolean>) {
        val layer = logicLayer
        val otherLayer = other.logicLayer
        if (layer > 0) {
            expressBuilder.append(" or ")
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

    fun and(init: AndGroup.() -> Unit) {
        val scope = AndGroup()
        scope.init()
        or(scope)
    }

}