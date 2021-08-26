package com.github.zhaofanzhe.kom.entity

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.express.Table
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Suppress("UNCHECKED_CAST")
abstract class Entity<T : Table<out Entity<T>>>(
    private val clazz: KClass<T>
) {

    internal fun newTable(): Table<out Entity<T>> {
        val constructor = clazz.constructors.firstOrNull { it.parameters.isEmpty() }
        return constructor?.call() ?: throw KomException("cant create Table ${clazz.qualifiedName} .")
    }

    internal fun values(): MutableMap<String, Any?> {
        val values = mutableMapOf<String, Any?>()

        val properties = this::class.memberProperties
            .mapNotNull { it as? KMutableProperty1<Any, Any?> }

        properties.forEach {
            it.isAccessible = true
            values[it.name] = it.get(this)
            it.isAccessible = false
        }

        return values
    }

}