package com.github.zhaofanzhe.kom.flavor

import com.github.zhaofanzhe.kom.KomException
import com.github.zhaofanzhe.kom.connection.ConnectionFactory
import com.github.zhaofanzhe.kom.express.Column
import java.util.*

interface Flavor {

    companion object {

        fun getFlavor(factory: ConnectionFactory): Flavor {
            val connection = factory.getConnection()
            val productName = connection.metaData.databaseProductName
//            val productVersion = connection.metaData.databaseProductVersion
            connection.close()

            val loader = ServiceLoader.load(Flavor::class.java).toList()

            println(loader)

            return loader.firstOrNull { it.productName == productName }
                ?: throw KomException("Unknown productName: $productName")
        }

    }

    val productName: String

    // 获取类型信息
    fun typedef(column: Column<*, *>): String?

    fun name(name: String): String

}