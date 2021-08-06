@file:Suppress("UNCHECKED_CAST")

package io.github.zhaofanzhe.kom.queryer.filler

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class AnyFiller(private val instance: Any) : Filler<Any> {

    override fun set(field: String, value: Any) {
        val optional = instance::class.memberProperties.stream()
            .filter { it.name == field }
            .filter { it != null }
            .findAny()

        if (!optional.isPresent) {
            return
        }

        val property = optional.get() as KMutableProperty1<Any, Any>

        val isAccessible = property.isAccessible

        if (!isAccessible) property.isAccessible = true

        property.set(instance, value)

        if (!isAccessible) property.isAccessible = false
    }

    override fun getInstance(): Any {
        return instance
    }

}