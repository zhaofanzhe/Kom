package com.github.zhaofanzhe.kom.expression

data class ArgumentExpression<T>(val value: T) : ScalarExpression<T>()