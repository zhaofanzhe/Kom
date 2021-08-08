package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.queryer.QuerySource
import io.github.zhaofanzhe.kom.queryer.filler.Filler

class Tuple(private val querySource: QuerySource) {

    private val map = HashMap<Declare<*>, Any?>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(declare: Declare<T>): T? {
        return map[declare] as T
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(table: Table<T>): T {
        val filler = Filler.create<T>(querySource.to(table))
        table.columns().forEach {
            filler.set(it, this[it])
        }
        return filler.getInstance()
    }

    operator fun set(key: Declare<*>, value: Any?) {
        map[key] = value
    }

    override fun toString(): String {
        return map.toString()
    }

}