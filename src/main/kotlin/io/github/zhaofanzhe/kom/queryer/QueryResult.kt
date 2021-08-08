@file:Suppress("DuplicatedCode")

package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.queryer.filler.Filler
import java.sql.ResultSet

class QueryResult(private val resultSet: ResultSet) {

    fun <T : Any> fetchOne(source: QuerySource): T? {
        if (!resultSet.next()) {
            return null
        }
        val filler = Filler.create<T>(source)
        source.declares.forEach { (column, name) ->
            filler.set(column, resultSet.getObject(name))
        }
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
        return list
    }

}