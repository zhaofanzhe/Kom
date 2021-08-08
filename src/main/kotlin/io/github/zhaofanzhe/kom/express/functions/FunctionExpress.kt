package io.github.zhaofanzhe.kom.express.functions

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.Express
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult
import io.github.zhaofanzhe.kom.express.declare.Declare

class FunctionExpress(
    private val name: String,
    private val args: Array<Any>,
) : Express() {

    override fun generate(context: Context, result: ExpressResult): IExpressResult {

        result += name
        result += "("

        args.forEachIndexed { index, value ->
            if (index > 0) {
                result += ", "
            }
            when (value) {
                is Express -> {
                    result += value.generate(context)
                }
                is Declare<*> -> {
                    result += value.express().generate(context)
                }
                else -> {
                    result.append("?", value)
                }
            }
        }

        result += ")"
        return result
    }

}