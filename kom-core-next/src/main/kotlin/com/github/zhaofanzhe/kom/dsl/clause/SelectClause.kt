package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.Bundle

class SelectClause(
    internal val selectables: List<Selectable>
) : Clause {

    override fun generateClause(): Bundle {
        val bundles = selectables.map { it.generateSelectable() }
        val sql = "select ${bundles.joinToString(separator = ", ") { it.sql }}"
        val args = mutableListOf<Any?>()
        bundles.forEach { args.addAll(it.args) }
        return Bundle(
            sql = sql,
            args = args,
        )
    }

}