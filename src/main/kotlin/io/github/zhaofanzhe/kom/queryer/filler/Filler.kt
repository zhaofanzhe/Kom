@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.express.Tuple
import io.github.zhaofanzhe.kom.queryer.QuerySource
import kotlin.reflect.KClass

interface Filler<T> {

    companion object {

        fun <T : Any> create(querySource: QuerySource): Filler<T> {
            return when (val source = querySource.source()) {
                is Table<*> -> {
                    TableFiller(newInstance(source.entityClass())) as Filler<T>
                }
                Tuple::class -> {
                    TupleFiller(Tuple(querySource)) as Filler<T>
                }
                else -> {
                    throw KomException("undefined source ${source::class.qualifiedName}")
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

    fun set(column: Column<*>, value: Any)

    fun getInstance(): T

}