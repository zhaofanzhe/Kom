package com.github.zhaofanzhe.kom.queryer

import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ITable
import com.github.zhaofanzhe.kom.express.Tuple
import com.github.zhaofanzhe.kom.express.declare.Declare
import kotlin.reflect.KClass

class QuerySource(
    private val context: Context,
    internal val table: ITable<*>?,
    private val select: List<Declare<*>>,
) {

    val declares: Map<Declare<*>, String> by lazy {
        val declares = mutableMapOf<Declare<*>, String>()
        select.forEach {
            declares[it] = context.currentDeclareAlias(it)
        }
        declares
    }

    fun entityClass(): KClass<*> {
        return table?.entityClass ?: Tuple::class
    }

    internal fun to(table: ITable<*>): QuerySource {
        return QuerySource(
            context = context,
            select = table.declares(),
            table = table,
        )
    }

}