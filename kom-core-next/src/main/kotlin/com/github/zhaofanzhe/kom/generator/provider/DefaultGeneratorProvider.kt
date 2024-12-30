package com.github.zhaofanzhe.kom.generator.provider

import com.github.zhaofanzhe.kom.expression.*
import com.github.zhaofanzhe.kom.expression.BinaryExpression
import com.github.zhaofanzhe.kom.generator.*

object DefaultGeneratorProvider : SampleGeneratorProvider() {

    init {
        register(ArgumentExpression::class, ArgumentGenerator())
        register(BinaryExpression::class, BinaryGenerator())
        register(ColumnExpression::class, ColumnGenerator())
        register(OrderByExpression::class, OrderByGenerator())
        register(QueryExpression::class, QueryGenerator())
        register(UnionExpression::class, UnionGenerator())
    }

}