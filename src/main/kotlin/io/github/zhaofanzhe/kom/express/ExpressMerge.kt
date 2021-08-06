package io.github.zhaofanzhe.kom.express

/**
 * 表达式合并
 */
class ExpressMerge(vararg exprs: Express?,separator: String = "") : Express() {

    private val express: String = exprs.filterNotNull().joinToString(separator = separator) { it.express() }
    private val params: Array<Any>

    init {
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