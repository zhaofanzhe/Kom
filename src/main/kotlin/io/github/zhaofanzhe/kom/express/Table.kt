package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.naming.Naming
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

abstract class Table<T : Any>(private val kClass: KClass<T>) {

    private val columns = mutableListOf<Column<*>>()

    internal fun declares(): Array<DeclareExpress> {
        return columns.map { DeclareExpress(it) }.toTypedArray()
    }

    internal fun tableName(): String {
        return Naming.toTableName(Naming.toEntityName(kClass))
    }

    internal fun entityClass(): KClass<T> {
        return this.kClass
    }

    fun <U> column(property: KMutableProperty1<T, U>, columnName: String = property.name): Column<U> {
        val column = Column<U>(fieldName = property.name,columnName = columnName)
        columns.add(column)
        return column
    }

}