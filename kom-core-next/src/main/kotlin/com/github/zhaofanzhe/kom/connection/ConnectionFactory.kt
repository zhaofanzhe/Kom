package com.github.zhaofanzhe.kom.connection

import java.sql.Connection

interface ConnectionFactory {
    fun getConnection(): Connection
}

fun <T> ConnectionFactory.execute(fn: (connection: Connection) -> T): T {
    val connection = getConnection()
    val result: T
    try {
        result = fn(connection)
    } catch (e: Throwable) {
        throw e
    } finally {
        connection.close()
    }
    return result
}