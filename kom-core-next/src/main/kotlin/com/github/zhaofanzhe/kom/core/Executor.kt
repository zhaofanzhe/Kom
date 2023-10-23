package com.github.zhaofanzhe.kom.core

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

class Executor(
    internal val factory: ConnectionFactory,
)

fun Executor.executeQuery(bundle: Bundle, selectables: List<Selectable>): QueryExecutor {
    return QueryExecutor(
        factory = factory,
        bundle = bundle,
        selectables = selectables,
    )
}

@Suppress("SqlSourceToSinkFlow")
fun Executor.execute(bundle: Bundle): Int {
    val connection = factory.getConnection()

    val prepareStatement = connection.prepareStatement(bundle.sql)

    bundle.args.forEachIndexed { index, value ->
        prepareStatement.setObject(index + 1, value)
    }

    val result = prepareStatement.executeUpdate()

    connection.close()

    return result
}