package com.github.zhaofanzhe.kom.dsl

import com.github.zhaofanzhe.kom.Database
import com.github.zhaofanzhe.kom.expression.QueryExpression
import com.github.zhaofanzhe.kom.expression.SqlExpression
import com.github.zhaofanzhe.kom.expression.UnionExpression
import com.github.zhaofanzhe.kom.generator.SqlGenerator
import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.Table

data class QueryContext(val expression: QueryExpression) {


    val sql: String
        get() = SqlGenerator.dispatch(expression).sql

}

fun Database.from(table: Table): QueryContext {
    return QueryContext(QueryExpression(table = table))
}

fun QueryContext.select(vararg columns: Column<*>): QueryContext {
    return this.copy(expression = expression.copy(columns = columns.toList()))
}

fun QueryContext.where(condition: () -> SqlExpression): QueryContext {
    return this.copy(expression = expression.copy(where = condition()))
}

fun QueryContext.union(block: () -> QueryContext): QueryContext {
    return this.copy(
        expression = expression.copy(
            unions = expression.unions + UnionExpression(
                expression = block().expression,
                isUnionAll = false
            )
        )
    )
}

fun QueryContext.unionAll(block: () -> QueryContext): QueryContext {
    return this.copy(
        expression = expression.copy(
            unions = expression.unions + UnionExpression(
                expression = block().expression,
                isUnionAll = true
            )
        )
    )
}

fun QueryContext.drop(offset: Int): QueryContext {
    return this.copy(expression = expression.copy(offset = offset))
}

fun QueryContext.take(limit: Int): QueryContext {
    return this.copy(expression = expression.copy(limit = limit))
}