package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.queryer.Queryer

class Database(private val connectionFactory: ConnectionFactory) {

    private fun query(): QueryClause<*> {
        return QueryClause<Void>(Queryer(connectionFactory))
    }

    fun <U : Any> select(table: Table<U>): QueryClause<U> {
        return query().select(table)
    }

    fun select(vararg tables: Table<*>): QueryClause<Tuple> {
        return query().select(*tables)
    }

    fun select(vararg declares: Declare<*>): QueryClause<Tuple> {
        return query().select(*declares)
    }

    fun <U : Any> selectFrom(clause: Table<U>): QueryClause<U> {
        return query().select(clause).from(clause)
    }

}