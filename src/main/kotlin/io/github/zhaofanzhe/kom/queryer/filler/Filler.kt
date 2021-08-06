@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.express.Tuple

interface Filler<T> {

    companion object {
        fun <T> create(instance: T): Filler<T> {
            return when (instance) {
                is Tuple -> {
                    TupleFiller(instance) as Filler<T>
                }
                is MutableMap<*, *> -> {
                    MutableMapFiller(instance as MutableMap<String, Any>) as Filler<T>
                }
                else -> {
                    AnyFiller(instance as Any) as Filler<T>
                }
            }
        }
    }

    fun set(field: String, value: Any)

    fun getInstance(): T

}