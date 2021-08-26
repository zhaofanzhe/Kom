package com.github.zhaofanzhe.kom.express.functions

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.Express
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.declare.Declare

class FunctionExpress(
    private val name: String,
    private val args: Array<Any>,
) : Express() {

    override fun generate(context: Context, result: ExpressResult) {

        result += name
        result += "("

        args.forEachIndexed { index, value ->
            if (index > 0) {
                result += ", "
            }
            when (value) {
                is Express -> {
                    value.generate(context, result)
                }
                is Declare<*> -> {
                    value.express().generate(context, result)
                }
                else -> {
                    result.append("?", value)
                }
            }
        }

        result += ")"
    }

}