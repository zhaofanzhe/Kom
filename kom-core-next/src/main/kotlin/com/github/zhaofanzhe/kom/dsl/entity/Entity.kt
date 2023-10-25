package com.github.zhaofanzhe.kom.dsl.entity

import com.github.zhaofanzhe.kom.*
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.express.eq
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.statement.dml.execute
import com.github.zhaofanzhe.kom.dsl.statement.dml.fetchOne
import com.github.zhaofanzhe.kom.dsl.statement.dml.set
import com.github.zhaofanzhe.kom.dsl.statement.dml.where
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.use
import com.github.zhaofanzhe.kom.dsl.useAnd
import com.github.zhaofanzhe.kom.exception.KomException
import com.github.zhaofanzhe.kom.toolkit.*
import kotlin.reflect.KClass

open class Entity<T : Table>(
    internal val clazz: KClass<T>
)

fun <T : Table> Database.create(entity: Entity<T>) {
    val table = ReflectionUtil.newInstance(entity.clazz)

    val entityFieldNames = ReflectionUtil.getEntityFieldNames(entity)

    var statement = insert(table)

    for (entityFieldName in entityFieldNames) {
        val column = ReflectionUtil.getTableColumn(table, entityFieldName) ?: continue
        val value = ReflectionUtil.getFieldValue(entity, entityFieldName) ?: continue
        statement = statement.set(column, value)
    }

    // 自增主键回填
    statement.execute()?.let {
        ReflectionUtil.setEntityPrimaryKey(table, entity, it)
    }

    // 回写默认数据
    for ((column, value) in statement.defaultValues) {
        val fieldName = ReflectionUtil.getTableFieldName(table, column) ?: continue
        ReflectionUtil.setFieldValue(entity, fieldName, value)
    }

}

fun <T : Table> Database.update(entity: Entity<T>) {
    val table = ReflectionUtil.newInstance(entity.clazz)

    val entityFieldNames = ReflectionUtil.getEntityFieldNames(entity)

    var statement = update(table)

    for (entityFieldName in entityFieldNames) {
        val column = ReflectionUtil.getTableColumn(table, entityFieldName) ?: continue
        val value = ReflectionUtil.getFieldValue(entity, entityFieldName)
        statement = statement.set(column, value)
    }

    // 回写数据
    for ((column, value) in statement.updateValues) {
        val fieldName = ReflectionUtil.getTableFieldName(table, column) ?: continue
        ReflectionUtil.setFieldValue(entity, fieldName, value)
    }

}

fun <T : Table> Database.delete(entity: Entity<T>, express: Express<Boolean>): Int {
    val table = ReflectionUtil.newInstance(entity.clazz)
    return delete(table).where(express).execute()
}

fun <T : Table> Database.delete(entity: Entity<T>, fn: (T) -> Express<Boolean>): Int {
    val table = ReflectionUtil.newInstance(entity.clazz)
    return delete(table).where(fn(table)).execute()
}

@Suppress("UNCHECKED_CAST")
fun <T : Table> Database.delete(entity: Entity<T>): Int {
    return delete(entity) { table ->
        useAnd {
            for (column in table.primaryKey) {
                val fieldName = ReflectionUtil.getTableFieldName(table, column) ?: continue
                val value = ReflectionUtil.getFieldValue(entity, fieldName)
                    ?: throw KomException("primary key ${column.name} value is null.")
                use((column as Column<Any?>) eq value.param)
            }
        }
    }
}

inline fun <reified E : Entity<T>, reified T : Table> Database.find(noinline fn: (T) -> Express<Boolean>): E? {
    return find(E::class, fn)
}

fun <E : Entity<T>, T : Table> Database.find(clazz: KClass<E>, fn: (T) -> Express<Boolean>): E? {
    val entity = ReflectionUtil.newInstance(clazz)
    val table = ReflectionUtil.newInstance(entity.clazz)
    val result = selectFrom(table).where(fn(table)).fetchOne() ?: return null
    for ((selectable, value) in result) {
        val column = selectable as? Column<*> ?: continue
        val fieldName = ReflectionUtil.getTableFieldName(table, column) ?: continue
        ReflectionUtil.setFieldValueWithConvert(entity, fieldName, value)
    }
    return entity
}