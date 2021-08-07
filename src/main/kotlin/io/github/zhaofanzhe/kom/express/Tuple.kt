package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.queryer.QuerySource
import io.github.zhaofanzhe.kom.queryer.filler.Filler
import java.lang.StringBuilder

class Tuple(private val querySource: QuerySource) {

    private val map = HashMap<Column<*>, Any?>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(column: Column<T>): T? {
        return map[column] as T
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(table: Table<T>): T {
        val filler = Filler.create<T>(querySource.to(table))
        table.columns().forEach {
            filler.set(it, this[it])
        }
        return filler.getInstance()
    }

    operator fun set(key: Column<*>, value: Any?) {
        map[key] = value
    }

    override fun toString(): String {
        return map.toString()
    }

}