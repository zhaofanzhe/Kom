package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.connection.ConnectionFactory

/**
 * 查询器
 */
class Queryer(private val connectionFactory: ConnectionFactory) {

    /**
     * 执行查询
     */
    fun executeQuery(sql: String, params: List<Any>): QueryResult {
        val connection = connectionFactory.getConnection()

        val prepareStatement = connection.prepareStatement(sql)

        params.forEachIndexed { index, value ->
            prepareStatement.setObject(index + 1, value)
        }

        val resultSet = prepareStatement.executeQuery()

        return QueryResult(
            resultSet = resultSet,
        )
    }

    fun execute(sql: String, params: List<Any>): Int {
        val connection = connectionFactory.getConnection()

        val prepareStatement = connection.prepareStatement(sql)

        params.forEachIndexed { index, value ->
            prepareStatement.setObject(index + 1, value)
        }

        return prepareStatement.executeUpdate()
    }

}