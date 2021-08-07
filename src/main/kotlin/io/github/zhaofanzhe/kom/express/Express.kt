package io.github.zhaofanzhe.kom.express

/**
 * 表达式
 */
abstract class Express {

    internal abstract fun generate(context: Context)

    internal abstract fun express(): String

    internal abstract fun params(): Array<Any>

    override fun toString(): String {
        return express()
    }

}