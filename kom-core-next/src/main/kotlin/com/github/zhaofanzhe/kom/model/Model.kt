package com.github.zhaofanzhe.kom.model

import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.FieldDelegate
import com.github.zhaofanzhe.kom.table.Table
import com.github.zhaofanzhe.kom.utils.NameUtils
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

// 基础模型类
interface Model {

    companion object

}

// 从模型类获取表信息
public fun <T : Model> Model.Companion.getTable(modelClass: KClass<T>): Table {
    val modelInstance = modelClass.constructors.first().call() // 获取类的实例
    val columns = modelClass.memberProperties.mapNotNull { property ->
        val delegate = property.withAccessible { it.getDelegate(modelInstance) } as? FieldDelegate<*>
        val column = delegate?.column
        column?.copy(name = property.name) // 将列名设为属性名
    }
    return Table(NameUtils.toTableName(modelClass.simpleName!!), columns)
}

private fun <T : KCallable<*>, R> T.withAccessible(block: (T) -> R): R {
    this.isAccessible = true
    val result = block(this)
    this.isAccessible = false
    return result
}

fun int(primaryKey: Boolean = false) =
    FieldDelegate(0, Column("", "INT", false, primaryKey))

fun intOrNull(primaryKey: Boolean = false) =
    FieldDelegate(null as Int?, Column("", "INT", true, primaryKey))

fun varchar(length: Int = 255) =
    FieldDelegate("", Column("", "VARCHAR", false, length = length))

fun varcharOrNull(length: Int = 255) =
    FieldDelegate(null as String?, Column("", "VARCHAR", true, length = length))