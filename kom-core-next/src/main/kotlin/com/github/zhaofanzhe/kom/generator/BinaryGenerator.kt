package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.BinaryExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class BinaryGenerator : SqlGenerator<BinaryExpression<*, *>>() {

    override fun generate(builder: SqlBuilder, expression: BinaryExpression<*, *>) {
        dispatch(builder, expression.left)
        builder.writeKeyword(" ${expression.operator} ")
        dispatch(builder, expression.right)
    }

}