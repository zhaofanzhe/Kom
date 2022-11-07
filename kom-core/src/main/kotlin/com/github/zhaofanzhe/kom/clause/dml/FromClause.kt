package com.github.zhaofanzhe.kom.clause.dml

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.ITable

class FromClause(
    private val table: ITable<*>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        result += "\nfrom "
        if (table is Clause) {
            table.generate(context, result)
        } else {
            result += context.currentTableName(table)
        }
        result += " as "
        result += context.currentTableAlias(table)
    }

}