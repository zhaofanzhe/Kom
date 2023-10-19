package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.core.Clause
import com.github.zhaofanzhe.kom.dsl.core.Express

class WhereClause(val express: Express<Boolean>) : Clause {

    override fun generateClause(): Bundle {
        val bundle = this.express.generateExpress()
        return Bundle(
            sql = "where ${bundle.sql}",
            args = bundle.args,
        )
    }

}