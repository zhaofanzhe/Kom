package io.github.zhaofanzhe.kom.express

/**
 * 表达式合并
 */
class ExpressMerge(private vararg val exprs: Express?,private val separator: String = "") : Express() {

    private lateinit var express: String
    private lateinit var params: Array<Any>

    override fun generate(context: Context) {
        exprs.filterNotNull().forEach { it.generate(context) }
        express = exprs.filterNotNull().joinToString(separator = separator) { it.express() }
        val params = mutableListOf<Any>()
        exprs.filterNotNull().forEach {
            params.addAll(it.params())
        }
        this.params = params.toTypedArray()
    }

    override fun express(): String {
        return express
    }

    override fun params(): Array<Any> {
        return params
    }

}