package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.ComparisonExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class ComparisonGenerator : SqlGenerator<ComparisonExpression<*>>() {

    override fun generate(builder: SqlBuilder, expression: ComparisonExpression<*>) {
        builder.writeKeyword("${expression.column.columnName} ${expression.operator} ${expression.value}")
    }

}