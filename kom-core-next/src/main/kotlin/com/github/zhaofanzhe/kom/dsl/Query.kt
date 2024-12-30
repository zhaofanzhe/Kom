package com.github.zhaofanzhe.kom.dsl

import com.github.zhaofanzhe.kom.Database
import com.github.zhaofanzhe.kom.expression.QueryExpression
import com.github.zhaofanzhe.kom.expression.ScalarExpression
import com.github.zhaofanzhe.kom.expression.UnionExpression
import com.github.zhaofanzhe.kom.generator.SqlGenerator
import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.Table

data class Query(val expression: QueryExpression) {


    val sql: String
        get() = SqlGenerator.dispatch(expression).sql

}

fun Database.from(table: Table): Query {
    return Query(QueryExpression(table = table))
}

fun Query.select(vararg columns: Column<*>): Query {
    return this.copy(expression = expression.copy(columns = columns.toList()))
}

fun Query.where(condition: () -> ScalarExpression<Boolean>): Query {
    return this.copy(expression = expression.copy(where = condition()))
}

fun Query.union(block: () -> Query): Query {
    return this.copy(
        expression = expression.copy(
            unions = expression.unions + UnionExpression(
                expression = block().expression.copy(
                    orderBy = emptyList(),
                    offset = null,
                    limit = null,
                ),
                isUnionAll = false
            )
        )
    )
}

fun Query.unionAll(block: () -> Query): Query {
    return this.copy(
        expression = expression.copy(
            unions = expression.unions + UnionExpression(
                expression = block().expression.copy(
                    orderBy = emptyList(),
                    offset = null,
                    limit = null,
                ),
                isUnionAll = true
            )
        )
    )
}

fun Query.drop(offset: Int): Query {
    return this.copy(expression = expression.copy(offset = offset))
}

fun Query.take(limit: Int): Query {
    return this.copy(expression = expression.copy(limit = limit))
}