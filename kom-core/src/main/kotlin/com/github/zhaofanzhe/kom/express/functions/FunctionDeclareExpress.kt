package com.github.zhaofanzhe.kom.express.functions

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.express.declare.DeclareExpress

@Suppress("DuplicatedCode")
class FunctionDeclareExpress(
    private val declare: Declare<*>
) : DeclareExpress() {

    override fun generate(context: Context, result: ExpressResult) {
        declare.express().generate(context, result)
        result += " as "
        result += context.currentDeclareAlias(declare)
    }

}