package io.github.zhaofanzhe.kom.connection

import java.sql.Connection

interface ConnectionFactory {
    fun getConnection(): Connection
}