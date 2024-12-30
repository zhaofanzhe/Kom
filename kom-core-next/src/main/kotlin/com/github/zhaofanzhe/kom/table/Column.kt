package com.github.zhaofanzhe.kom.table

import com.github.zhaofanzhe.kom.expression.ColumnExpression
import com.github.zhaofanzhe.kom.expression.ScalarExpression


class Column<T>(val columnName: String) : ColumnDeclaring<T> {

    override fun asExpression(): ScalarExpression<T> {
        return ColumnExpression(this)
    }

}