package io.github.zhaofanzhe.kom.clause.ddl

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Context
import io.github.zhaofanzhe.kom.express.ExpressResult
import io.github.zhaofanzhe.kom.express.Table

@Suppress("UNCHECKED_CAST")
class CreateTableClause<T : Any>(private val table: Table<T>) : Clause() {

    override fun generate(context: Context, result: ExpressResult) {
        result += "create table "
        result += table.tableName
        result += "("
        val columns = table.declares().map { it as Column<T, *> }
        columns.forEachIndexed { index, column ->
            result += "\n\t"
            if (index > 0) {
                result += ", "
            }

        }
        result += ")"
    }

}