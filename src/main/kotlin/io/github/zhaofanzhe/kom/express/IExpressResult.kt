package io.github.zhaofanzhe.kom.express

/**
 * 表达式结果接口
 */
interface IExpressResult {

    /**
     * 获取表达式
     */
    fun express(): String

    /**
     * 获取参数
     */
    fun params(): List<Any>

}