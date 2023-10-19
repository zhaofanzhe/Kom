package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.core.Express

class ComposeExpress(
    val op: String,
    val expresses: List<Express<Boolean>>,
    val brackets: Boolean = true
) : Express<Boolean> {

    override fun generateExpress(): Bundle {
        val bundles = expresses.map { it.generateExpress() }
        var sql = bundles.joinToString(separator = " $op ") { it.sql }
        if (this.brackets) {
            sql = "(${sql})"
        }
        val args = mutableListOf<Any?>()
        bundles.forEach { args.addAll(it.args) }
        return Bundle(
            sql = sql,
            args = args,
        )
    }

}