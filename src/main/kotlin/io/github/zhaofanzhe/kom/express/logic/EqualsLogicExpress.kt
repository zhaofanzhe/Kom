package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.Express
import io.github.zhaofanzhe.kom.express.LogicExpress

class EqualsLogicExpress(
    left: Any,
    right: Any,
) : LogicExpress<Boolean>() {

    init {
        if (left is Express) {
            expressBuilder.append(left.express())
        } else {
            expressBuilder.append("?")
            paramsBuilder.add(left)
        }
        expressBuilder.append(" = ")
        if (right is Express) {
            expressBuilder.append(right.express())
        } else {
            expressBuilder.append("?")
            paramsBuilder.add(right)
        }
    }

}