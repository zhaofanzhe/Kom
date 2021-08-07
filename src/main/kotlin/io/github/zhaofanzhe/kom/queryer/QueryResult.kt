@file:Suppress("DuplicatedCode")

package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.queryer.filler.Filler
import java.sql.ResultSet

class QueryResult(private val resultSet: ResultSet) {

    fun <T : Any> fetchOne(source: Any): T? {
        val metaData = resultSet.metaData
        if (!resultSet.next()) {
            return null
        }

        val filler = Filler.create<T>(source)

        for (i in 1..metaData.columnCount) {
            filler.set(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)))
        }
        return filler.getInstance()
    }

    fun <T : Any> fetchAll(source: Any): List<T> {
        val metaData = resultSet.metaData
        val list = ArrayList<T>()
        while (resultSet.next()) {
            val filler = Filler.create<T>(source)
            for (i in 1..metaData.columnCount) {
                filler.set(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)))
            }
            list += filler.getInstance()
        }
        return list
    }

}