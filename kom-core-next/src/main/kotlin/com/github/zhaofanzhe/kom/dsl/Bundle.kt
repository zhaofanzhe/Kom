package com.github.zhaofanzhe.kom.dsl

class Bundle(
    val sql: String,
    val args: List<Any?> = listOf()
)

fun mergeBundles(vararg bundles: Bundle, separator: String = ""): Bundle {
    val sql = bundles.joinToString(separator) { it.sql }
    val args = mutableListOf<Any?>()
    bundles.forEach { args.addAll(it.args) }
    return Bundle(sql, args)
}