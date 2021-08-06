package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.naming.Naming

abstract class Entity<T>(private val clazz: Class<T>) {

    private val fields = mutableListOf<Field<*>>()

    internal fun declares(): Array<DeclareExpress> {
        return fields.map { DeclareExpress(it) }.toTypedArray()
    }

    internal fun tableName(): String {
        return Naming.toTableName(Naming.toEntityName(clazz))
    }

    internal fun entityClass(): Class<T> {
        return this.clazz
    }

    fun <T> field(fieldName: String): Field<T> {
        val field = Field<T>(fieldName)
        fields.add(field)
        return field
    }

}