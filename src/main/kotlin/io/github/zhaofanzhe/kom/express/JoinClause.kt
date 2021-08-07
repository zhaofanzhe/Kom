package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.builder.ExpressBuilder

class JoinClause<T : Any>(
    private val genre: Genre,
    private val table: Table<*>,
    private val clause: QueryClause<T>,
) : ExpressBuilder() {

    private lateinit var express: LogicExpress<Boolean>

    enum class Genre(val join: String) {
        INNER("\ninner join "),
        LEFT("\nleft join "),
        RIGHT("\nright join "),
        FULL("\nfull join ");
    }

    fun on(express: LogicExpress<Boolean>): QueryClause<T> {
        this.express = express
        return clause.join(this)
    }

    override fun generate(context: Context) {
        super.generate(context)

        express.generate(context)

        expressBuilder.append(genre.join)
        expressBuilder.append(table.tableName())
        expressBuilder.append(" as ")
        expressBuilder.append(context.tableAliasGenerator.next(table))
        expressBuilder.append(" on ")
        expressBuilder.append(express.express())

        paramsBuilder.addAll(express.params())
    }

}