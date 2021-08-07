@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.express.Tuple
import kotlin.reflect.KClass

interface Filler<T> {

    companion object {

        fun <T:Any> create(source:Any): Filler<T> {
            return when (source) {
                is Table<*> -> {
                    TableFiller(source as Table<T>,newInstance(source.entityClass()))
                }
                Tuple::class -> {
                    TupleFiller(newInstance(source as KClass<Tuple>)) as Filler<T>
                }
                MutableMap::class -> {
                    MutableMapFiller(newInstance(source as KClass<MutableMap<String,Any>>)) as Filler<T>
                }
                else -> {
                    AnyFiller(newInstance(source as KClass<T>)) as Filler<T>
                }
            }
        }

        private fun <T : Any> newInstance(kClass: KClass<T>): T {
            val ctor = kClass.constructors.firstOrNull { it.parameters.isEmpty() }
            if (ctor != null) {
                return ctor.call()
            } else {
                throw KomException(
                    """No default constructor was found in class ${kClass.qualifiedName}"""
                )
            }
        }

    }

    fun set(key: String, value: Any)

    fun getInstance(): T

}