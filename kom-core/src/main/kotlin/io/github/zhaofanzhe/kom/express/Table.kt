package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.functions.Function
import io.github.zhaofanzhe.kom.express.identifier.Identifier
import io.github.zhaofanzhe.kom.naming.Naming
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.jvmErasure

abstract class Table<T : Any>(
    override val entityClass: KClass<T>,
    override val tableName: String = Naming.toTableName(Naming.toEntityName(entityClass))
) : ITable<T> {

    private var columns = mutableListOf<Column<T, *>>()

    override fun declares(): List<Declare<*>> {
        return columns
    }

    fun <U : Any> column(
        property: KMutableProperty1<T, U>,
        columnName: String = Naming.toColumnName(property.name),
    ): Column<T, U> {
        val column = Column<T, U>(
            clazz = property.returnType.jvmErasure,
            name = columnName,
            fieldName = property.name,
            nullable = property.returnType.isMarkedNullable,
            table = this
        )
        columns.add(column)
        return column
    }

    override fun refs(): List<ITable<*>>? {
        // nothing to do.
        return null
    }

    override fun primaryKeys(): List<Column<T, *>> {
        return columns.filter { it.primaryKey }
    }

}