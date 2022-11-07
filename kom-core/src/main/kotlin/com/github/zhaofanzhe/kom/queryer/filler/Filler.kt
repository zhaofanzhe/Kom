@file:Suppress("UNCHECKED_CAST")

package com.github.zhaofanzhe.kom.queryer.filler

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.express.Tuple
import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.queryer.QuerySource
import kotlin.reflect.KClass

interface Filler<T> {

    companion object {

        fun <T : Any> create(source: QuerySource): Filler<T> {
            return when (source.entityClass()) {
                Tuple::class -> {
                    TupleFiller(Tuple(source)) as Filler<T>
                }

                else -> {
                    TableFiller(newInstance(source.entityClass())) as Filler<T>
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

    fun set(declare: Declare<*>, value: Any?)

    fun getInstance(): T

}