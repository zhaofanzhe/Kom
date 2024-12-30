package com.github.zhaofanzhe.kom.expression

data class UnionExpression(
    val expression: QuerySourceExpression,
    val isUnionAll: Boolean,
) : SqlExpression()