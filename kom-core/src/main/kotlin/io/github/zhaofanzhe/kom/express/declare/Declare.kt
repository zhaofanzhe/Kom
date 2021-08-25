package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.express.Express
import io.github.zhaofanzhe.kom.express.ITable

/**
 * 声明
 *  - 字段
 *  - 函数
 */
interface Declare<T : Any?> {

    /**
     * 声明表达式
     */
    fun declare(): DeclareExpress

    /**
     * 普通表达式
     */
    fun express(): Express

    /**
     * 新表声明
     */
    fun newTableDeclare(table: ITable<*>): Declare<T>

    val ref: Declare<T>?

    val name: String

    val table: ITable<*>

}