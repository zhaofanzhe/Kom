package com.github.zhaofanzhe.kom.express

import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.queryer.QuerySource
import com.github.zhaofanzhe.kom.queryer.filler.Filler


class Tuple(private val source: QuerySource) {

    private val map = HashMap<Declare<*>, Any?>()

    private fun find(declare: Declare<*>): Declare<*>? {
        for (entry in map) {
            var root: Declare<*>? = entry.key
            while (true) {
                if (root == null) break
                if (root === declare) {
                    return entry.key
                }
                root = root.ref
            }
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T:Any?> get(declare: Declare<T>): T {
        return map[find(declare)] as T
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(table: Table<T>): T {
        val filler = Filler.create<T>(source.to(table))
        table.declares().forEach {
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