package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.functions.Function
import io.github.zhaofanzhe.kom.express.identifier.Identifier
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
     * 获取单主键
     * 如果复合主键不会返回
     */
    fun singlePrimaryKey(): Column<T,*>?

    /**
     * 获取查询数量引用
     */
    fun count(): Function<Number> {
        val primaryKey = singlePrimaryKey()
        val args: Array<Any> = if (primaryKey != null) {
            arrayOf(primaryKey)
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