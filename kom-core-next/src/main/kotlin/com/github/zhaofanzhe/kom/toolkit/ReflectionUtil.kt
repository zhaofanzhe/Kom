package com.github.zhaofanzhe.kom.toolkit

import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.entity.Entity
import com.github.zhaofanzhe.kom.dsl.table.Table
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.isAccessible

/**
 * 反射工具
 */
class ReflectionUtil {

    companion object

}

/**
 * safeAccessibleScope
 */
private fun <T, P : KProperty1<*, *>> ReflectionUtil.Companion.safeAccessibleScope(
    property: P,
    fn: P.() -> T
): T {
    val accessible = property.isAccessible
    property.isAccessible = true
    val result = property.fn()
    property.isAccessible = accessible
    return result
}

/**
 * 获取字段的值
 */
@Suppress("UNCHECKED_CAST")
fun ReflectionUtil.Companion.getFieldValue(instance: Any, field: String): Any? {
    val property = instance::class.memberProperties.find { it.name == field }
            as? KProperty1<Any, Any?> ?: return null
    return safeAccessibleScope(property) {
        get(instance)
    }
}

/**
 * 设置字段的值
 */
@Suppress("UNCHECKED_CAST")
fun ReflectionUtil.Companion.setFieldValue(instance: Any, field: String, value: Any?) {
    val property = instance::class.memberProperties.find { it.name == field }
            as? KMutableProperty1<Any, Any?> ?: return
    safeAccessibleScope(property) {
        set(instance, value)
    }
}

/**
 * 获取 entity 中所有的字段名称
 */
fun <E : Entity<*>> ReflectionUtil.Companion.getEntityFieldNames(entity: E): List<String> {
    return entity::class.memberProperties.map { it.name }.filter { it != "clazz" }
}

/**
 * 获取 table 中所有的 column 的字段名称
 */
fun ReflectionUtil.Companion.getTableFieldNames(table: Table): List<String> {
    return table::class.memberProperties
        .filter { Column::class.starProjectedType.isSupertypeOf(it.returnType) }
        .map { it.name }
}

/**
 * 根据 column 获取 table 中的字段名称
 */
fun ReflectionUtil.Companion.getTableFieldName(table: Table, column: Column<*>): String? {
    val fieldNames = getTableFieldNames(table)
    for (fieldName in fieldNames) {
        if (getTableColumn(table, fieldName) == column) {
            return fieldName
        }
    }
    return null
}

/**
 * 获取 table 中的 column
 */
fun ReflectionUtil.Companion.getTableColumn(table: Table, field: String): Column<*>? {
    return getFieldValue(table, field) as? Column<*> ?: return null
}

/**
 * 设置 entity 的主键
 */
fun ReflectionUtil.Companion.setEntityPrimaryKey(table: Table, entity: Entity<*>, value: Long) {
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
    val pk = when (pkType) {
        Byte::class.starProjectedType -> value.toByte()
        Short::class.starProjectedType -> value.toShort()
        Int::class.starProjectedType -> value.toInt()
        Long::class.starProjectedType -> value
        else -> throw RuntimeException("无法转换类型")
    }

    // 主键回写
    ReflectionUtil.setFieldValue(entity, fieldName, pk)
}