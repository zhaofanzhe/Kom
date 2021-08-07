package io.github.zhaofanzhe.kom.express.logic

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.Express
import io.github.zhaofanzhe.kom.express.LogicExpress

class NotEqualsLogicExpress(
    private val left: Any,
    private val right: Any,
) : LogicExpress<Boolean>() {

    override fun generate(context: Context) {
        super.generate(context)

        if (left is Express){
            left.generate(context)
            expressBuilder.append(left.express())
        } else {
            expressBuilder.append("?")
            paramsBuilder.add(left)
        }
        expressBuilder.append(" != ")
        if (right is Express){
            right.generate(context)
            expressBuilder.append(right.express())
        } else {
            expressBuilder.append("?")
            paramsBuilder.add(right)
        }
    }

}