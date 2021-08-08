package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.declare.FunctionDeclareExpress
import io.github.zhaofanzhe.kom.express.functions.CountFunctionExpress
import io.github.zhaofanzhe.kom.express.functions.Function

fun count(column: Column<*>): Function<Number> {
    return Function {
        it.declareExpress = FunctionDeclareExpress(it)
        it.functionExpress = CountFunctionExpress(column.columnExpress())
    }
}

fun countAll(): Function<Number> {
    return Function {
        it.declareExpress = FunctionDeclareExpress(it)
        it.functionExpress = CountFunctionExpress("*")
    }
}