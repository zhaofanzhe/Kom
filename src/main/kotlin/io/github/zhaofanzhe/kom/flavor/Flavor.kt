package io.github.zhaofanzhe.kom.flavor

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.connection.ConnectionFactory
import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.support.mariadb.MariaDBFlavor
import io.github.zhaofanzhe.kom.support.mysql.MySQLFlavor
import io.github.zhaofanzhe.kom.support.postgresql.PostgreSQLFlavor
import kotlin.reflect.KClass

interface Flavor {

    companion object {

        fun getFlavor(factory: ConnectionFactory): Flavor {
            val connection = factory.getConnection()
            val productName = connection.metaData.databaseProductName
            val productVersion = connection.metaData.databaseProductVersion
            connection.close()
            when (productName) {
                "MySQL" -> {
                    if (productVersion.contains("MariaDB")) {
                        return MariaDBFlavor()
                    }
                    return MySQLFlavor()
                }
                "PostgreSQL" -> {
                    return PostgreSQLFlavor()
                }
                else -> throw KomException("Unknown productName: $productName")
            }
        }

    }

    // 获取类型信息
    fun typedef(column:Column<*,*>): String?

    fun name(name: String): String

}