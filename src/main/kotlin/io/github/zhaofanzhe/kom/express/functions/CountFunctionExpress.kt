package io.github.zhaofanzhe.kom.express.functions

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.Express

@Suppress("DuplicatedCode")
class CountFunctionExpress(private val arg: Any) : FunctionExpress() {

    override fun generate(context: Context) {
        super.generate(context)

        expressBuilder.append("count(")
        if (arg is Express) {
            arg.generate(context)
            expressBuilder.append(arg.express())
            paramsBuilder.addAll(arg.params())
        } else {
            expressBuilder.append(arg)
        }
        expressBuilder.append(")")

    }

}