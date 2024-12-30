package com.github.zhaofanzhe.kom.expression

class BinaryExpression<T, R>(
    val left: ScalarExpression<T>,
    val operator: String,
    val right: ScalarExpression<T>,
) : ScalarExpression<R>()