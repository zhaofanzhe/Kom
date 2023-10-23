package com.github.zhaofanzhe.kom.dsl.selectable

import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle


class SelectableAlias(
    val alias: String,
    val selectable: Selectable,
) : Selectable {

    override fun generateSelectable(): Bundle {
        val bundle = selectable.generateSelectable()
        val sql = "(${bundle.sql}) as $alias"
        val args = bundle.args
        return Bundle(
            sql = sql,
            args = args,
        )
    }

    override fun flatName(): String = alias

}

fun Selectable.alias(alias: String): SelectableAlias {
    if (this is SelectableAlias) {
        throw RuntimeException("禁止重复调用 alias 方法")
    }
    return SelectableAlias(alias, this)
}
