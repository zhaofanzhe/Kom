package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
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
    fun refs():List<ITable<*>>?

}