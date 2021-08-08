package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.ColumnDeclareExpress
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress
import io.github.zhaofanzhe.kom.naming.Naming
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

abstract class Table<T : Any>(private val kClass: KClass<T>, private val tableName: String = "") : ITable<T> {

    private val columns = mutableListOf<Column<*>>()

    override fun declares(): List<Declare<*>> {
        return columns.toList()
    }

    override fun declareExpress(): Array<DeclareExpress> {
        return columns.map { ColumnDeclareExpress(it) }.toTypedArray()
    }

    override fun tableName(): String {
        if (tableName != "") {
            return tableName
        }
        return Naming.toTableName(Naming.toEntityName(kClass))
    }

    override fun entityClass(): KClass<T> {
        return this.kClass
    }

    override fun source(): Any? {
        val entityClass = this.entityClass()
        if (entityClass == Tuple::class){
            return entityClass
        }
        return this
    }

    fun <U> column(
        property: KMutableProperty1<T, U>,
        columnName: String = Naming.toColumnName(property.name)
    ): Column<U> {
        val column = Column<U>(table = this, fieldName = property.name, columnName = columnName)
        columns.add(column)
        return column
    }

}