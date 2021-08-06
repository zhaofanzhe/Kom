package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.queryer.Queryer

class Database(private val connectionFactory: ConnectionFactory) {

    private fun query(): QueryClause<*> {
        return QueryClause<Void>(Queryer(connectionFactory))
    }

    fun <U : Any> select(clause: Table<U>): QueryClause<U> {
        return query().select(clause)
    }

    fun select(vararg fields: Field<*>): QueryClause<Tuple> {
        return query().select(*fields)
    }

    fun <U : Any> selectFrom(clause: Table<U>): QueryClause<U> {
        return query().select(clause).from(clause)
    }

}