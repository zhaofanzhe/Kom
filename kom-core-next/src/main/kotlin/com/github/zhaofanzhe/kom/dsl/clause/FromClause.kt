package com.github.zhaofanzhe.kom.dsl.clause

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.table.TableRef

class FromClause(
    val tableRef: TableRef
) : Clause {

    override fun generateClause(): Bundle {
        return Bundle("from ${tableRef.tableDefine()}")
    }

}