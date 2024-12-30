package com.github.zhaofanzhe.kom.generator.provider

import com.github.zhaofanzhe.kom.expression.*
import com.github.zhaofanzhe.kom.generator.*

object DefaultGeneratorProvider : SampleGeneratorProvider() {

    init {
        register(ArgumentExpression::class, ArgumentGenerator())
        register(ComparisonExpression::class, ComparisonGenerator())
        register(LogicalExpression::class, LogicalGenerator())
        register(OrderByExpression::class, OrderByGenerator())
        register(QueryExpression::class, QueryGenerator())
        register(UnionExpression::class, UnionGenerator())
    }

}