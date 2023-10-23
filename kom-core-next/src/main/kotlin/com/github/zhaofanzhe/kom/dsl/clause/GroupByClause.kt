package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.column.Column
import com.github.zhaofanzhe.kom.dsl.Bundle

class GroupByClause(
    val columns: List<Column<*>>,
) : Clause {

    override fun generateClause(): Bundle {
        return Bundle("group by ${columns.joinToString(separator = ", ") { it.generateSelectable().sql }}")
    }

}