package com.github.zhaofanzhe.kom.dsl.entity

import com.github.zhaofanzhe.kom.Database
import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.statement.dml.execute
import com.github.zhaofanzhe.kom.dsl.statement.dml.set
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.insert
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.isAccessible

open class Entity<T : Table>(
    internal val clazz: KClass<T>
)

@Suppress("UNCHECKED_CAST")
private fun <T : Any> getPropertyValue(instance: T, name: String): Any? {
    val property = instance::class.memberProperties.find { it.name == name }
            as? KProperty1<Any, Any?> ?: return null
    property.isAccessible = true
    val value = property.get(instance)
    property.isAccessible = false
    return value
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> setPropertyValue(instance: T, name: String, value: Any?) {
    val property = instance::class.memberProperties.find { it.name == name }
            as? KMutableProperty1<Any, Any?> ?: return
    property.isAccessible = true
    property.set(instance, value)
    property.isAccessible = false
}

@Suppress("UNCHECKED_CAST")
fun <T : Table> Database.create(entity: Entity<T>) {

    val constructor = entity.clazz.constructors.find { it.parameters.isEmpty() }
        ?: throw RuntimeException("未找到默认构造器")

    val table = constructor.call()

    val entityProperties = entity::class.memberProperties
        .mapNotNull { it as? KMutableProperty1<Any, Any?> }

    var expr = insert(table)

    for (entityProperty in entityProperties) {
        val column = getPropertyValue(table, entityProperty.name) as? Column<*> ?: continue
        val value = getPropertyValue(entity, entityProperty.name)
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
    val fieldName = entity.clazz.memberProperties.find { getPropertyValue(table, it.name) == column }?.name ?: return

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
    setPropertyValue(entity, fieldName, value)
}