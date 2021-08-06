package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.naming.Naming
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

abstract class Table<T : Any>(private val kClass: KClass<T>) {

    private val fields = mutableListOf<Column<*>>()

    internal fun declares(): Array<DeclareExpress> {
        return fields.map { DeclareExpress(it) }.toTypedArray()
    }

    internal fun tableName(): String {
        return Naming.toTableName(Naming.toEntityName(kClass))
    }

    internal fun entityClass(): KClass<T> {
        return this.kClass
    }

    fun <U> field(property: KMutableProperty1<T, U>): Column<U> {
        val field = Column<U>(property.name)
        fields.add(field)
        return field
    }

}