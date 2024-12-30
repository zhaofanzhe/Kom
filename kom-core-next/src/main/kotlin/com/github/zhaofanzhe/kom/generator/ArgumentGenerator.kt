package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.ArgumentExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class ArgumentGenerator : SqlGenerator<ArgumentExpression>() {

    override fun generate(builder: SqlBuilder, expression: ArgumentExpression) {
        builder.writeKeyword("?")
        builder.writeParameters(expression.value)
    }

}