package com.github.zhaofanzhe.kom.core

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import org.slf4j.LoggerFactory
import java.sql.Statement

class Executor(
    internal val factory: ConnectionFactory,
) {

    internal val logger = LoggerFactory.getLogger(Executor::class.java)

}

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

    logger.debug("sql: {}", bundle.sql)
    logger.debug("args: {}", bundle.args)

    val prepareStatement = connection.prepareStatement(bundle.sql)

    bundle.args.forEachIndexed { index, value ->
        prepareStatement.setObject(index + 1, value)
    }

    val result = prepareStatement.executeUpdate()

    connection.close()

    return result
}

@Suppress("SqlSourceToSinkFlow")
fun Executor.executeCreate(bundle: Bundle): Long? {
    val connection = factory.getConnection()

    logger.debug("sql: {}", bundle.sql)
    logger.debug("args: {}", bundle.args)

    val prepareStatement = connection.prepareStatement(bundle.sql, Statement.RETURN_GENERATED_KEYS)

    bundle.args.forEachIndexed { index, value ->
        prepareStatement.setObject(index + 1, value)
    }

    prepareStatement.executeUpdate()

    val generatedKeys = prepareStatement.generatedKeys

    val result = if (generatedKeys.next()) {
        generatedKeys.getLong(1)
    } else {
        null
    }

    connection.close()

    return result
}