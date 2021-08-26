package com.github.zhaofanzhe.kom.metadata

import com.github.zhaofanzhe.kom.getConnectionFactory

fun main() {
    val connectionFactory = getConnectionFactory()

    val schemaMetadata = ActualSchemaMetadata(connectionFactory)

    println(schemaMetadata.databaseProductName)
    println(schemaMetadata.databaseProductVersion)

    val tables = schemaMetadata.tables()

    tables.forEach { it ->
        it.columns().forEach {
            println("${it.schemaName}.${it.tableName}.${it.columnName}")
        }
    }

}