package io.github.zhaofanzhe.kom.clause.dml

import io.github.zhaofanzhe.kom.clause.Clause
import io.github.zhaofanzhe.kom.express.*

class JoinClause<Q : Clause>(
    private val genre: Genre,
    private val table: ITable<*>,
    private val clause: JoinClauseLink<Q>,
) : Express() {

    private lateinit var express: LogicExpress<Boolean>

    enum class Genre(val join: String) {
        INNER("\ninner join "),
        LEFT("\nleft join "),
        RIGHT("\nright join "),
        FULL("\nfull join ");
    }

    fun on(express: LogicExpress<Boolean>): Q {
        this.express = express
        return clause.join(this, table)
    }

    override fun generate(context: Context, result: ExpressResult) {
        result += genre.join

        if (table is Express) {
            table.generate(context, result)
        } else {
            result += table.tableName
        }
        result += " as "
        result += context.currentTableAlias(table)
        result += " on "
        express.generate(context, result)
    }

}