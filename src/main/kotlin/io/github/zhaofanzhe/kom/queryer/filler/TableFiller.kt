package io.github.zhaofanzhe.kom.queryer.filler

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.declare.Declare
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Suppress("UNCHECKED_CAST")
open class TableFiller<T : Any>(private val instance: T) : Filler<T> {

    override fun set(declare: Declare<*>, value: Any?) {

        if (value == null) return

        var root = declare

        while (true) {
            val prototype = root.ref ?: break
            root = prototype
        }

        if (root !is Column<*>) {
            throw KomException("declare is not Column")
        }

        val optional = instance::class.memberProperties.stream()
            .filter { it.name == root.fieldName }
            .filter { it != null }
            .findAny()

        if (!optional.isPresent) {
            return
        }

        val property = optional.get() as KMutableProperty1<Any, Any?>

        val isAccessible = property.isAccessible

        if (!isAccessible) property.isAccessible = true

        property.set(instance, value)

        if (!isAccessible) property.isAccessible = false

    }

    override fun getInstance(): T {
        return instance
    }

}