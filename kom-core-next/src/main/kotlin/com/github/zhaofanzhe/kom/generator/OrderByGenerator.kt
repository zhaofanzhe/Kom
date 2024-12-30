package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.OrderByExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class OrderByGenerator : SqlGenerator<OrderByExpression>() {

    override fun generate(builder: SqlBuilder, expression: OrderByExpression) {
        builder.writeKeyword(expression.column.columnName)
    }

}