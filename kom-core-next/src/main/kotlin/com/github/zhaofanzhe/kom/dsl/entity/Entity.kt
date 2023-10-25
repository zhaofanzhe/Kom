package com.github.zhaofanzhe.kom.dsl.entity

import com.github.zhaofanzhe.kom.Database
import com.github.zhaofanzhe.kom.delete
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.express.eq
import com.github.zhaofanzhe.kom.dsl.express.param
import com.github.zhaofanzhe.kom.dsl.statement.dml.execute
import com.github.zhaofanzhe.kom.dsl.statement.dml.set
import com.github.zhaofanzhe.kom.dsl.statement.dml.where
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.use
import com.github.zhaofanzhe.kom.dsl.useAnd
import com.github.zhaofanzhe.kom.exception.KomException
import com.github.zhaofanzhe.kom.insert
import com.github.zhaofanzhe.kom.toolkit.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

open class Entity<T : Table>(
    internal val clazz: KClass<T>
)

private fun <T : Table> Database.newTable(entity: Entity<T>): T {
    val constructor = entity.clazz.constructors.find { it.parameters.isEmpty() }
        ?: throw RuntimeException("未找到默认构造器")
    return constructor.call()
}

fun <T : Table> Database.create(entity: Entity<T>) {
    val table = newTable(entity)

    val entityFieldNames = ReflectionUtil.getEntityFieldNames(entity)

    var expr = insert(table)

    for (entityFieldName in entityFieldNames) {
        val column = ReflectionUtil.getTableColumn(table, entityFieldName) ?: continue
        val value = ReflectionUtil.getFieldValue(entity, entityFieldName)
        expr = expr.set(column, value)
    }

    // 自增主键
    val number = expr.execute() ?: return

    // 主键字段数量不是1
    if (table.primaryKey.size != 1) {
        return
    }

    // 取出列
    val column = table.primaryKey.find { true }!!

    // 不是自增主键
    if (!column.isAutoIncrement) {
        return
    }

    // table 中主键的 字段名
    val fieldName = ReflectionUtil.getTableFieldName(table, column) ?: return

    // 主键类型
    val pkType = entity::class.memberProperties.find { it.name == fieldName }?.returnType ?: return

    // 转换数据
    val value = when (pkType) {
        Byte::class.starProjectedType -> number.toByte()
        Short::class.starProjectedType -> number.toShort()
        Int::class.starProjectedType -> number.toInt()
        Long::class.starProjectedType -> number
        else -> throw RuntimeException("无法转换类型")
    }

    // 主键回写
    ReflectionUtil.setFieldValue(entity, fieldName, value)
}

fun <T : Table> Database.delete(entity: Entity<T>, express: Express<Boolean>): Int {
    val table = newTable(entity)
    return delete(table).where(express).execute()
}

fun <T : Table> Database.delete(entity: Entity<T>, fn: (T) -> Express<Boolean>): Int {
    val table = newTable(entity)
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