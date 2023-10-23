package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.Bundle

class LimitClause(
    internal val offset: Int,
    internal val limit: Int,
) : Clause {

    override fun generateClause(): Bundle {
        if (offset == 0 && limit > 0) {
            return Bundle("limit $limit")
        }
        return Bundle("limit ${offset},${limit}")
    }

}