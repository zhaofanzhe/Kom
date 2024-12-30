package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.QueryExpression
import com.github.zhaofanzhe.kom.utils.SqlBuilder

class QueryGenerator : SqlGenerator<QueryExpression>() {

    override fun generate(builder: SqlBuilder, expression: QueryExpression) {
        if (!expression.columns.isNullOrEmpty()) {
            builder.writeKeyword("SELECT ${expression.columns.joinToString(separator = ", ") { it.columnName }}")

        } else {
            builder.writeKeyword("SELECT *")
        }
        builder.writeKeyword(" FROM ${expression.table.tableName}")
        expression.where?.let {
            builder.writeKeyword(" WHERE ")
            dispatch(builder, it)
        }
        expression.unions.forEach {
            dispatch(builder, it)
        }
        if (expression.orderBy.isNotEmpty()) {
            builder.writeKeyword(" ORDER BY ")
            expression.orderBy.forEachIndexed { index, orderBy ->
                if (index > 0) {
                    builder.writeKeyword(", ")
                }
                dispatch(builder, orderBy)
            }
        }
        if (expression.limit != null) {
            builder.writeKeyword(" LIMIT ?")
            builder.writeParameters(expression.limit)
        }
        if (expression.offset != null) {
            builder.writeKeyword(" OFFSET ?")
            builder.writeParameters(expression.offset)
        }
    }

}