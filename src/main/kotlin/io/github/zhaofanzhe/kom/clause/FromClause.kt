package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.IExpressResult
import io.github.zhaofanzhe.kom.express.ITable

class FromClause(
    private val table: ITable<*>
) : Clause() {

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += "\nfrom "
        if (table is Clause) {
            result += table.generate(context)
        } else {
            result += context.currentTableName(table)
        }
        result += " as "
        result += context.currentTableAlias(table)
        return result
    }

}