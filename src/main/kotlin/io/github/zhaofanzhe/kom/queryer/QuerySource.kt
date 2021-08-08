package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.declare.Declare

class QuerySource(
    private val context: Context,
    private val source: Any
) {

    internal val columns: Map<Declare<*>, String> by lazy {
        val columns = mutableMapOf<Declare<*>, String>()
        context.columnAliasGenerator.columns.forEach { it ->
            it.value.forEach {
                columns[it.key] = it.value
            }
        }
        context.columnAliasGenerator.others.forEach {
            columns[it.key] = it.value
        }
        columns
    }

    internal fun source(): Any {
        return source
    }

    internal fun to(source: Any): QuerySource {
        return QuerySource(
            context = context,
            source = source,
        )
    }

}