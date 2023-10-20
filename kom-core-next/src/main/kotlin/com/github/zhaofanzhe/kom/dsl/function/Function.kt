package com.github.zhaofanzhe.kom.dsl.function

import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle
import com.github.zhaofanzhe.kom.dsl.express.Express


open class Function<R>(
    val name: String,
    val args: List<Express<*>>
) : Express<R> {

    override fun generateExpress(): Bundle {
        val bundles = this.args.map { it.generateExpress() }
        val sql = "${name}(${bundles.joinToString(separator = ", ") { it.sql }})"
        val args = mutableListOf<Any?>()
        bundles.forEach { args.addAll(it.args) }
        return Bundle(
            sql = sql,
            args = args,
        )
    }

}
