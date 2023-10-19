package com.github.zhaofanzhe.kom.connection

import java.sql.Connection

interface ConnectionFactory {
    fun getConnection(): Connection
}