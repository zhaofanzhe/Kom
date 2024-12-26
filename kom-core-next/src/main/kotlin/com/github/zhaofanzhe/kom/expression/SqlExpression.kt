@file:Suppress("unused")

package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.Table

sealed class SqlExpression

data class ArgumentExpression(val value: Any?) : SqlExpression()

data class Comparison<T>(
    val column: Column<T>,
    val operator: String,
    val value: Any
) : SqlExpression()

data class LogicalExpression(
    val left: SqlExpression,
    val operator: String,
    val right: SqlExpression
) : SqlExpression()

infix fun <T : Any> Column<T>.eq(value: T): Comparison<T> = Comparison(this, "=", value)

infix fun <T : Any> Column<T>.ne(value: T): Comparison<T> = Comparison(this, "<>", value)

infix fun <T : Any> Column<T>.gt(value: T): Comparison<T> = Comparison(this, ">", value)

infix fun <T : Any> Column<T>.lt(value: T): Comparison<T> = Comparison(this, "<", value)

infix fun SqlExpression.and(other: SqlExpression): LogicalExpression = LogicalExpression(this, "AND", other)

infix fun SqlExpression.or(other: SqlExpression): LogicalExpression = LogicalExpression(this, "OR", other)

data class SelectExpression(
    var table: Table,
    var columns: List<Column<*>>? = null,
    var where: SqlExpression? = null
) : SqlExpression()