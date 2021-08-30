package com.github.zhaofanzhe.kom.queryer

import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import java.sql.Statement

/**
 * 查询器
 */
class Queryer(private val connectionFactory: ConnectionFactory) {

    /**
     * 执行查询
     */
    fun executeQuery(sql: String, params: List<Any?>): QueryResult {
        val connection = connectionFactory.getConnection()

        val prepareStatement = connection.prepareStatement(sql)

        params.forEachIndexed { index, value ->
            prepareStatement.setObject(index + 1, value)
        }

        return QueryResult(
            connection = connection,
            resultSet = prepareStatement.executeQuery(),
        )
    }

    /**
     * 执行创建
     */
    fun executeCreate(sql: String, params: List<Any?>): QueryResult {
        val connection = connectionFactory.getConnection()

        val prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

        params.forEachIndexed { index, value ->
            prepareStatement.setObject(index + 1, value)
        }

        prepareStatement.executeUpdate()

        return QueryResult(
            connection = connection,
            resultSet = prepareStatement.generatedKeys,
        )
    }

    /**
     * 执行
     */
    fun execute(sql: String, params: List<Any?>): Int {
        val connection = connectionFactory.getConnection()

        val prepareStatement = connection.prepareStatement(sql)

        params.forEachIndexed { index, value ->
            prepareStatement.setObject(index + 1, value)
        }

        val result = prepareStatement.executeUpdate()
        connection.close()
        return result
    }

}