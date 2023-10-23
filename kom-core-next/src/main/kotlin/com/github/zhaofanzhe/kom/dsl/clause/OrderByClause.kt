package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.express.SortExpress

class OrderByClause(
    vararg val expresses: SortExpress
) : Clause {

    override fun generateClause(): Bundle {
        return Bundle(
            sql = "order by ${this.expresses.joinToString(separator = ", ") { it.generateExpress().sql }}"
        )
    }

}