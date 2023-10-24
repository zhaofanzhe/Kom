package com.github.zhaofanzhe.kom.core

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.connection.execute
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import java.sql.ResultSet

class QueryExecutor(
    internal val factory: ConnectionFactory,
    internal val bundle: Bundle,
    internal val selectables: List<Selectable>
)

@Suppress("SqlSourceToSinkFlow")
private fun <T> QueryExecutor.execute(fn: (resultSet: ResultSet) -> T): T {
    return factory.execute { connection ->
        val prepareStatement = connection.prepareStatement(bundle.sql)
        bundle.args.forEachIndexed { index, value ->
            prepareStatement.setObject(index + 1, value)
        }
        return@execute fn(prepareStatement.executeQuery())
    }
}

fun QueryExecutor.fetchOne(): QueryResult? {
    return this.execute { resultSet ->
        if (!resultSet.next()) {
            return@execute null
        }
        val result = QueryResult()
        this.selectables.forEachIndexed { index, selectable ->
            result[selectable] = resultSet.getObject(index);
        }
        return@execute result
    }
}

fun QueryExecutor.fetchAll(): List<QueryResult> {
    return this.execute { resultSet ->
        val list = ArrayList<QueryResult>()
        while (resultSet.next()) {
            val result = QueryResult()
            this.selectables.forEachIndexed { index, selectable ->
                result[selectable] = resultSet.getObject(index + 1);
            }
            list += result
        }
        return@execute list
    }
}