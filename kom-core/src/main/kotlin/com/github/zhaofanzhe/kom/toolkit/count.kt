package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.express.Column
import com.github.zhaofanzhe.kom.express.functions.Function

fun count(column: Column<*, *>): Function<Number> {
    return Function(
        table = column.table,
        functionName = "count",
        functionArgs = arrayOf(column),
    )
}