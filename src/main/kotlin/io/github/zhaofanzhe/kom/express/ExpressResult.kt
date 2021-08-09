package io.github.zhaofanzhe.kom.express

import java.lang.StringBuilder

/**
 * 表达式结果
 */
class ExpressResult : IExpressResult {

    private val express = StringBuilder()

    private val params = mutableListOf<Any?>()

    fun append(express: String, vararg args: Any?) {
        this.express.append(express)
        this.params.addAll(args)
    }

    fun append(result: IExpressResult) {
        this.express.append(result.express())
        this.params.addAll(result.params())
    }

    fun append(separator: String = "", results: List<IExpressResult>) {
        this.express.append(results.joinToString(separator = separator) { it.express() })
        results.forEach {
            this.params.addAll(it.params())
        }
    }

    operator fun plusAssign(result: IExpressResult?) {
        if (result == null) return
        append(result)
    }

    operator fun plusAssign(results: List<IExpressResult?>) {
        results.forEach { this += it }
    }

    operator fun plusAssign(express: String) {
        this.express.append(express)
    }

    override fun express(): String {
        return this.express.toString()
    }

    override fun params(): List<Any?> {
        return this.params
    }

}