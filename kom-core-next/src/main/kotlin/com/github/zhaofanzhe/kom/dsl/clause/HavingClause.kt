package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.Bundle

class HavingClause(
    val express: Express<Boolean>
) : Clause {

    override fun generateClause(): Bundle {
        val bundle = express.generateExpress()
        val sql = "having ${bundle.sql}"
        val args = bundle.args
        return Bundle(
            sql = sql,
            args = args
        )
    }

}