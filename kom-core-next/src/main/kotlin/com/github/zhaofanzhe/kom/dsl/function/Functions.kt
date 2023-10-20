package com.github.zhaofanzhe.kom.dsl.function

import com.github.zhaofanzhe.kom.dsl.column.Column

fun max(column: Column<out Number>): SelectableFunction<Number> {
    return SelectableFunction("max", listOf(column))
}

fun min(column: Column<out Number>): SelectableFunction<Number> {
    return SelectableFunction("min", listOf(column))
}

fun sum(column: Column<out Number>): SelectableFunction<Number> {
    return SelectableFunction("sum", listOf(column))
}

fun count(column: Column<*>): SelectableFunction<Number> {
    return SelectableFunction("count", listOf(column))
}