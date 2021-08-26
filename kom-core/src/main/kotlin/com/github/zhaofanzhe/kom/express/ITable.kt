package com.github.zhaofanzhe.kom.express

import com.github.zhaofanzhe.kom.express.declare.Declare
import com.github.zhaofanzhe.kom.express.functions.Function
import com.github.zhaofanzhe.kom.express.identifier.Identifier
import kotlin.reflect.KClass

/**
 * 表接口
 */
interface ITable<T : Any> {

    /**
     * 实体类
     */
    val entityClass: KClass<T>?

    /**
     * 获取表名
     */
    val tableName: String

    /**
     * 获取列定义
     */
    fun declares(): List<Declare<*>>

    /**
     * 表引用
     */
    fun refs(): List<ITable<*>>?

    /**
     * 如果复合主键不会返回
     */
    fun primaryKeys(): List<Column<T,*>>

    /**
     * 获取查询数量引用
     */
    fun count(): Function<Number> {
        val primaryKeys = primaryKeys()
        val args: Array<Any> = if (primaryKeys.size == 1) {
            arrayOf(primaryKeys)
        } else {
            arrayOf(Identifier<Number>(this, "*"))
        }
        return Function(
            table = this,
            functionName = "count",
            functionArgs = args,
        )
    }

}