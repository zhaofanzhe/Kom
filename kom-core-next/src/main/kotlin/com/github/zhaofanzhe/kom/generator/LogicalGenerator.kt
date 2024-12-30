package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.LogicalExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class LogicalGenerator : SqlGenerator<LogicalExpression>() {

    override fun generate(builder: SqlBuilder, expression: LogicalExpression) {
        dispatch(builder, expression.left)
        builder.writeKeyword(expression.operator)
        dispatch(builder, expression.right)
    }

}