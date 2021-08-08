package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.functions.Function

@Suppress("DuplicatedCode")
class FunctionDeclareExpress(
    private val func: Function<*>,
) : DeclareExpress() {

    override fun generate(context: Context) {
        super.generate(context)

        val fc = func.func()
        fc.generate(context)

       expressBuilder.append(fc.express())
       paramsBuilder.addAll(fc.params())

        expressBuilder.append(" as ")
        expressBuilder.append(context.columnAliasGenerator.next(func))
    }

}