package com.github.zhaofanzhe.kom.clause.ddl

import com.github.zhaofanzhe.kom.clause.Clause
import com.github.zhaofanzhe.kom.express.Context
import com.github.zhaofanzhe.kom.express.ExpressResult
import com.github.zhaofanzhe.kom.express.Table
import com.github.zhaofanzhe.kom.flavor.Flavor
import com.github.zhaofanzhe.kom.queryer.Queryer

class DropTableClause<T : Any>(
    private val queryer: Queryer,
    private val flavor: Flavor,
    private val table: Table<T>,
) : Clause() {


    override fun generate(context: Context, result: ExpressResult) {
        result += "drop table "
        result += flavor.name(table.tableName)
    }

    fun execute(): Int {
        val result = ExpressResult()
        generate(Context(flavor), result)
        return queryer.execute(result.express(), result.params())
    }

    override fun toString(): String {
        val context = Context(flavor)
        val result = ExpressResult()
        generate(context, result)
        return result.express()
    }

}