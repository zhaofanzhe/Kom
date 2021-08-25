package io.github.zhaofanzhe.kom.tool

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Computable<T>(private val compute: () -> T) : ReadOnlyProperty<Any?, T> {

    private var completed = false

    private var value: T? = null

    fun <T> observable(initialValue: T): ReadWriteProperty<Any?, T> {
        return Delegates.observable(initialValue) { _, _, _ ->
            completed = false
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (!completed) {
            value = compute()
            completed = true
        }
        return value!!
    }

    fun update(value: T) {
        this.completed = true
        this.value = value
    }

}