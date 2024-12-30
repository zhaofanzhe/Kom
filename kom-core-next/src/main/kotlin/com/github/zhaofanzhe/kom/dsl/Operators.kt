package com.github.zhaofanzhe.kom.dsl

import com.github.zhaofanzhe.kom.expression.ArgumentExpression
import com.github.zhaofanzhe.kom.expression.BinaryExpression
import com.github.zhaofanzhe.kom.expression.ScalarExpression
import com.github.zhaofanzhe.kom.table.ColumnDeclaring

infix fun <T : Any> ScalarExpression<T>.and(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    BinaryExpression(this, "AND", right)

infix fun <T : Any> ScalarExpression<T>.or(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    BinaryExpression(this, "OR", right)

infix fun <T : Any> ColumnDeclaring<T>.eq(value: T): ScalarExpression<Boolean> =
    this eq ArgumentExpression(value)

infix fun <T : Any> ColumnDeclaring<T>.eq(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    this.asExpression() eq right

infix fun <T : Any> ScalarExpression<T>.eq(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    BinaryExpression(this, "=", right)

infix fun <T : Any> ScalarExpression<T>.ne(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    BinaryExpression(this, "<>", right)

infix fun <T : Any> ScalarExpression<T>.gt(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    BinaryExpression(this, ">", right)

infix fun <T : Any> ScalarExpression<T>.lt(right: ScalarExpression<T>): ScalarExpression<Boolean> =
    BinaryExpression(this, "<", right)
