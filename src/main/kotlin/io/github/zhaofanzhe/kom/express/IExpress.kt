package io.github.zhaofanzhe.kom.express

/**
 * 表达式接口
 */
interface IExpress {

    /**
     * 生成表达式结果
     */
    fun generate(context: Context): IExpressResult

}