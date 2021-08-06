package io.github.zhaofanzhe.kom.express.builder

import io.github.zhaofanzhe.kom.express.Express

@Suppress("MemberVisibilityCanBePrivate")
open class ExpressBuilder : Express() {

    internal val expressBuilder = StringBuilder()

    internal val paramsBuilder = mutableListOf<Any>()

    override fun express(): String {
        return expressBuilder.toString()
    }

    override fun params(): Array<Any> {
        return paramsBuilder.toTypedArray()
    }

}