package com.github.zhaofanzhe.kom.utils

import com.github.zhaofanzhe.kom.model.Model
import com.github.zhaofanzhe.kom.table.FieldDelegate
import com.github.zhaofanzhe.kom.table.Table
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

object TableUtil {

    fun <T : Model> getTable(modelClass: KClass<T>): Table {
        val modelInstance = modelClass.constructors.first().call() // 获取类的实例
        val columns = modelClass.memberProperties.mapNotNull { property ->
            val delegate = property.withAccessible { it.getDelegate(modelInstance) } as? FieldDelegate<*>
            val column = delegate?.column
            column?.copy(name = NameUtil.toColumnName(property.name))
        }
        return Table(NameUtil.toTableName(modelClass.simpleName!!), columns)
    }

}

