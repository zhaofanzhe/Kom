package io.github.zhaofanzhe.kom.clause

import io.github.zhaofanzhe.kom.express.*

class JoinClause<T : Any>(
    private val genre: Genre,
    private val table: ITable<*>,
    private val clause: QueryClause<T>,
) : Express() {

    private lateinit var express: LogicExpress<Boolean>

    enum class Genre(val join: String) {
        INNER("\ninner join "),
        LEFT("\nleft join "),
        RIGHT("\nright join "),
        FULL("\nfull join ");
    }

    fun on(express: LogicExpress<Boolean>): QueryClause<T> {
        this.express = express
        return clause.join(this, table)
    }

    override fun generate(context: Context, result: ExpressResult): IExpressResult {
        result += genre.join

        if (table is Express) {
            result += table.generate(context)
        } else {
            result += table.tableName
        }
        result += " as "
        result += context.currentTableAlias(table)
        result += " on "
        result += express.generate(context)
        return result
    }

}