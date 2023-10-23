package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.table.TableRef

class JoinClause(
    val joinType: String,
    val tableRef: TableRef,
    val express: Express<Boolean>,
) : Clause {

    override fun generateClause(): Bundle {
        val bundle = express.generateExpress()
        return Bundle(
            sql = "$joinType ${tableRef.tableDefine()} on ${bundle.sql}",
            args = bundle.args,
        )
    }

}