package io.github.zhaofanzhe.kom.express.builder

import io.github.zhaofanzhe.kom.express.Clause
import io.github.zhaofanzhe.kom.express.Context

@Suppress("MemberVisibilityCanBePrivate")
open class ClauseExpressBuilder : Clause() {

    internal val expressBuilder = StringBuilder()

    internal val paramsBuilder = mutableListOf<Any>()

    override fun generate(context: Context) {
        expressBuilder.clear()
        paramsBuilder.clear()
    }

    override fun express(): String {
        return expressBuilder.toString()
    }

    override fun params(): Array<Any> {
        return paramsBuilder.toTypedArray()
    }

}