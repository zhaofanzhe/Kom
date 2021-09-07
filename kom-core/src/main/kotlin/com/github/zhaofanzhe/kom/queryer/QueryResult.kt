@file:Suppress("DuplicatedCode")

package com.github.zhaofanzhe.kom.queryer

import com.github.zhaofanzhe.kom.queryer.filler.Filler
import java.sql.Connection
import java.sql.ResultSet

class QueryResult(
    private val connection: Connection,
    private val resultSet: ResultSet,
) {

    fun <T : Any> fetchOne(source: QuerySource): T? {
        if (!resultSet.next()) {
            return null
        }
        val filler = Filler.create<T>(source)
        source.declares.forEach { (column, name) ->
            filler.set(column, resultSet.getObject(name))
        }
        connection.close()
        return filler.getInstance()
    }

    fun <T : Any> fetchAll(source: QuerySource): List<T> {
        val list = ArrayList<T>()
        while (resultSet.next()) {
            val filler = Filler.create<T>(source)
            source.declares.forEach { (column, name) ->
                filler.set(column, resultSet.getObject(name))
            }
            list += filler.getInstance()
        }
        connection.close()
        return list
    }

    fun fetchPrimaryKey(): Long? {

        if (!resultSet.next()) {
            return null
        }

        /**
         * Mysql,MariaDB,PostgreSQL: "GENERATED_KEY"
         * SQLite: "last_insert_rowid()"
         * currently used temporarily the number 1.
         */
        val result = resultSet.getLong(1)

        connection.close()

        return result

    }

}