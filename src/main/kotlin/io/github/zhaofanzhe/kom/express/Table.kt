package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.functions.Function
import io.github.zhaofanzhe.kom.express.identifier.Identifier
import io.github.zhaofanzhe.kom.naming.Naming
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

abstract class Table<T : Any>(
    override val entityClass: KClass<T>,
    override val tableName: String = Naming.toTableName(Naming.toEntityName(entityClass))
) : ITable<T> {

    private var columns = mutableListOf<Column<T, *>>()

    private val primaryKeys = mutableListOf<Column<T, *>>()

    override fun declares(): List<Declare<*>> {
        return columns
    }

    fun <U : Any> column(
        property: KMutableProperty1<T, U>,
        columnName: String = Naming.toColumnName(property.name),
        primaryKey: Boolean = false,
    ): Column<T, U> {
        val column = Column<T, U>(
            name = columnName,
            fieldName = property.name,
            table = this
        )
        columns.add(column)
        if (primaryKey) {
            primaryKeys += columns
        }
        return column
    }

    override fun refs(): List<ITable<*>>? {
        // nothing to do.
        return null
    }

    override fun primaryKeys(): List<Column<T, *>> {
        return primaryKeys
    }

}