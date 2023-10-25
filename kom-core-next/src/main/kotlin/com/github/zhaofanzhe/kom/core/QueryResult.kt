package com.github.zhaofanzhe.kom.core

import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.toolkit.ReflectionUtil
import com.github.zhaofanzhe.kom.toolkit.newInstance
import com.github.zhaofanzhe.kom.toolkit.setFieldValue
import kotlin.reflect.KClass

class QueryResult : HashMap<Selectable, Any?>()

inline fun <reified T : Any> List<QueryResult>.fill(): List<T> {
    return map { it.fill<T>() }
}

inline fun <reified T : Any> QueryResult.fill(): T {
    return this.fill(T::class)
}

fun <T : Any> QueryResult.fill(clazz: KClass<T>): T {
    val instance = ReflectionUtil.newInstance(clazz)
    for ((key, value) in this.entries) {
        val flatName = key.flatName() ?: continue
        ReflectionUtil.setFieldValue(instance, flatName, value)
    }
    return instance
}

