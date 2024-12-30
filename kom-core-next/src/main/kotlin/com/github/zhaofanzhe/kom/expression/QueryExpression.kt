package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.Table

data class QueryExpression(
    val table: Table,
    val columns: List<Column<*>>? = null,
    val where: SqlExpression? = null,
    val unions: List<UnionExpression> = emptyList(),
    val orderBy: List<OrderByExpression> = emptyList(),
    val offset: Int? = null,
    val limit: Int? = null,
) : QuerySourceExpression()
