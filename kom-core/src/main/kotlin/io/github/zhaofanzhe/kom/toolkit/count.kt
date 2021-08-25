package io.github.zhaofanzhe.kom.toolkit

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.functions.Function

fun count(column: Column<*,*>): Function<Number> {
    return Function(
        table = column.table,
        functionName = "count",
        functionArgs = arrayOf(column),
    )
}