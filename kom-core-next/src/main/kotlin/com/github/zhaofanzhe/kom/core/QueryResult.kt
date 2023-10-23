package com.github.zhaofanzhe.kom.core

import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.exception.KomException
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class QueryResult : HashMap<Selectable, Any?>()

inline fun <reified T:Any> List<QueryResult>.flat():List<T>{
    return map { it.flat<T>() }
}

inline fun <reified T : Any> QueryResult.flat(): T {
    return flat(T::class)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> QueryResult.flat(clazz: KClass<T>): T {
    val constructor = clazz.constructors.find { it.parameters.isEmpty() } ?: throw KomException("未发现默认构造器")
    val instance = constructor.call()
    for ((key, value) in this.entries) {
        val flatName = key.flatName() ?: continue
        val member = clazz.memberProperties.find { it.name == flatName } ?: continue
        if (member !is KMutableProperty1) continue
        val property = member as KMutableProperty1<Any, Any?>
        property.isAccessible = true
        property.set(instance, value)
        property.isAccessible = false
    }
    return instance
}

