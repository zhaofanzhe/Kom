package com.github.zhaofanzhe.kom

import com.github.zhaofanzhe.kom.expression.SelectExpression
import com.github.zhaofanzhe.kom.expression.SqlExpression
import com.github.zhaofanzhe.kom.expression.SqlGenerator
import com.github.zhaofanzhe.kom.expression.generate
import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.Table

data class Query<T : SqlExpression>(val expression: T) {

    val sql: String
        get() = SqlGenerator.generate(expression).sql

}

fun from(table: Table): Query<SelectExpression> {
    return Query(SelectExpression(table = table))
}

fun Query<SelectExpression>.select(vararg columns: Column<*>): Query<SelectExpression> {
    return this.copy(expression = expression.copy(columns = columns.toList()))
}

fun Query<SelectExpression>.where(condition: () -> SqlExpression): Query<SelectExpression> {
    return this.copy(expression = expression.copy(where = condition()))
}