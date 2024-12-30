package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.ColumnExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class ColumnGenerator : SqlGenerator<ColumnExpression<*>>() {

    override fun generate(builder: SqlBuilder, expression: ColumnExpression<*>) {
        builder.writeKeyword(expression.column.columnName)
    }

}