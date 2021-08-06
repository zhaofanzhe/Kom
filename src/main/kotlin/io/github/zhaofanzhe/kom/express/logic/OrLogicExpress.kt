package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.LogicExpress
import kotlin.math.max

class OrLogicExpress(
    left: LogicExpress,
    right: LogicExpress,
) : LogicExpress() {

    init {
        val leftLayer = left.logicLayer
        val rightLayer = right.logicLayer

        if (leftLayer > 0) {
            expressBuilder.append("(")
        }
        expressBuilder.append(left.express())
        if (leftLayer > 0) {
            expressBuilder.append(")")
        }

        expressBuilder.append(" or ")

        if (rightLayer > 0) {
            expressBuilder.append("(")
        }
        expressBuilder.append(right.express())
        if (rightLayer > 0) {
            expressBuilder.append(")")
        }

        paramsBuilder.addAll(left.params())
        paramsBuilder.addAll(right.params())

        logicLayer = max(leftLayer, rightLayer) + 1
    }

}