package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.UnionExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class UnionGenerator : SqlGenerator<UnionExpression>() {

    override fun generate(builder: SqlBuilder, expression: UnionExpression) {
        if (expression.isUnionAll) {
            builder.writeKeyword(" UNION ALL ")
        } else {
            builder.writeKeyword(" UNION ")
        }
        dispatch(builder, expression.expression)
    }

}