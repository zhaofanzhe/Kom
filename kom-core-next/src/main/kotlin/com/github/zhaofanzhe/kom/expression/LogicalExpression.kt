package com.github.zhaofanzhe.kom.expression

data class LogicalExpression(
    val left: SqlExpression,
    val operator: String,
    val right: SqlExpression
) : SqlExpression()

infix fun SqlExpression.and(other: SqlExpression): LogicalExpression = LogicalExpression(this, "AND", other)

infix fun SqlExpression.or(other: SqlExpression): LogicalExpression = LogicalExpression(this, "OR", other)
