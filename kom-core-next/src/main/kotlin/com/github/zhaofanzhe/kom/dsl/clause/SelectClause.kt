package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.core.Clause
import com.github.zhaofanzhe.kom.dsl.core.Selectable

class SelectClause(
    private vararg val selectables: Selectable
) : Clause {

    override fun generateClause(): Bundle {
        return Bundle("select ${selectables.joinToString(", ") { it.generateSelectable() }}")
    }

}