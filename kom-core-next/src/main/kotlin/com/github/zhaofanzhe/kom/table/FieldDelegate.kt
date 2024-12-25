package com.github.zhaofanzhe.kom.table

import kotlin.reflect.KProperty

class FieldDelegate<T>(private var value: T, val column: Column) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}
