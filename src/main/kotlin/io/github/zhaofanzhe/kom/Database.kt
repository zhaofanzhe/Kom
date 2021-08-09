package io.github.zhaofanzhe.kom

import io.github.zhaofanzhe.kom.clause.*
import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.Tuple
import io.github.zhaofanzhe.kom.express.*
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.queryer.Queryer

class Database(private val connectionFactory: ConnectionFactory) {

    private val queryer = Queryer(connectionFactory)

    fun <T : Any> insert(table: Table<T>): InsertClause<T> {
        return InsertClause(queryer, table)
    }

    fun <T : Any> update(table: Table<T>): UpdateClause<T> {
        return UpdateClause(queryer, table)
    }

    private fun query(): QueryClause<*> {
        return QueryClause<Void>(queryer)
    }

    fun <U : Any> select(table: ITable<U>): QueryClause<U> {
        return query().select(table)
    }

    fun select(vararg tables: ITable<*>): QueryClause<Tuple> {
        return query().select(*tables)
    }

    fun select(vararg declares: Declare<*>): QueryClause<Tuple> {
        return query().select(*declares)
    }

    fun <U : Any> selectFrom(clause: ITable<U>): QueryClause<U> {
        return query().select(clause).from(clause)
    }

}