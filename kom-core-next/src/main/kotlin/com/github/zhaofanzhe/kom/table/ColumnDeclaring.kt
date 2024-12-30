package com.github.zhaofanzhe.kom.table

import com.github.zhaofanzhe.kom.expression.ScalarExpression

interface ColumnDeclaring<T> {

    public fun asExpression(): ScalarExpression<T>

}