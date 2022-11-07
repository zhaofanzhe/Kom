package com.github.zhaofanzhe.kom.express

/**
 * 表达式结果
 */
class ExpressResult {

    private val express = StringBuilder()

    private val params = mutableListOf<Any?>()

    fun append(express: String, vararg args: Any?) {
        this.express.append(express)
        this.params.addAll(args)
    }

    fun append(result: ExpressResult) {
        this.express.append(result.express())
        this.params.addAll(result.params())
    }

    operator fun plusAssign(express: String) {
        this.express.append(express)
    }

    fun express(): String {
        return this.express.toString()
    }

    fun params(): List<Any?> {
        return this.params
    }

}