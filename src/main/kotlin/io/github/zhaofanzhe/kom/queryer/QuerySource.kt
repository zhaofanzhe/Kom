package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.declare.Declare

class QuerySource(
    private val context: Context,
    private val select: Array<Declare<*>>,
    private val source: Any,
) {

    val declares: Map<Declare<*>, String> by lazy {

        val map = mutableMapOf<Declare<*>, String>()
        context.columnAliasGenerator.columns.forEach { it ->
            it.value.forEach {
                map[it.key] = it.value
            }
        }
        context.columnAliasGenerator.others.forEach {
            map[it.key] = it.value
        }

        val list = mutableMapOf<Declare<*>, String>()
        select.forEach {
            list[it] = map[it]!!
        }
        list
    }

    internal fun source(): Any {
        return source
    }

    internal fun to(source: Any): QuerySource {
        return QuerySource(
            context = context,
            select = select,
            source = source,
        )
    }

}