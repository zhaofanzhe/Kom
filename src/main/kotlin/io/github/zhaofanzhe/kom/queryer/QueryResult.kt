@file:Suppress("DuplicatedCode")

package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.queryer.filler.Filler
import java.sql.ResultSet

class QueryResult(private val resultSet: ResultSet) {

    fun <T : Any> fetchOne(querySource: QuerySource): T? {
        if (!resultSet.next()) {
            return null
        }

        val filler = Filler.create<T>(querySource)

        querySource.columns.forEach { (column, name) ->
            filler.set(column, resultSet.getObject(name))
        }

        return filler.getInstance()
    }

    fun <T : Any> fetchAll(querySource: QuerySource): List<T> {
        val list = ArrayList<T>()
        while (resultSet.next()) {
            val filler = Filler.create<T>(querySource)
            querySource.columns.forEach { (column, name) ->
                filler.set(column, resultSet.getObject(name))
            }
            list += filler.getInstance()
        }
        return list
    }

}