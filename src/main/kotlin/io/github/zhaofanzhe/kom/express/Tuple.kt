package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.queryer.QuerySource
import java.lang.StringBuilder

class Tuple(private val querySource: QuerySource) {

    private val map = HashMap<Column<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: Column<T>): T? {
        return map[key] as T
    }

    operator fun set(key: Column<*>, value: Any) {
        map[key] = value
    }

    override fun toString(): String {
        val builder = StringBuilder()
        return map.toString()
    }

}