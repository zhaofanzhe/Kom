package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.table.Column

data class ComparisonExpression<T>(
    val column: Column<T>,
    val operator: String,
    val value: Any
) : SqlExpression()


infix fun <T : Any> Column<T>.eq(value: T): ComparisonExpression<T> = ComparisonExpression(this, "=", value)

infix fun <T : Any> Column<T>.ne(value: T): ComparisonExpression<T> = ComparisonExpression(this, "<>", value)

infix fun <T : Any> Column<T>.gt(value: T): ComparisonExpression<T> = ComparisonExpression(this, ">", value)

infix fun <T : Any> Column<T>.lt(value: T): ComparisonExpression<T> = ComparisonExpression(this, "<", value)
