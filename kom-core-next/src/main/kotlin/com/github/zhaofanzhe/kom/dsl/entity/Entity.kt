package com.github.zhaofanzhe.kom.dsl.entity

import com.github.zhaofanzhe.kom.Database
import com.github.zhaofanzhe.kom.dsl.statement.dml.execute
import com.github.zhaofanzhe.kom.dsl.statement.dml.set
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.insert
import com.github.zhaofanzhe.kom.toolkit.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

open class Entity<T : Table>(
    internal val clazz: KClass<T>
)

fun <T : Table> Database.create(entity: Entity<T>) {

    val constructor = entity.clazz.constructors.find { it.parameters.isEmpty() }
        ?: throw RuntimeException("未找到默认构造器")

    val table = constructor.call()

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