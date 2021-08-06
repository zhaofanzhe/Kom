@file:Suppress("DuplicatedCode")

package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.queryer.filler.Filler
import java.sql.ResultSet

class QueryResult(private val resultSet: ResultSet) {

    inline fun <reified T> fetchOne(): T? {
        return fetchOne(T::class.java)
    }

    fun <T> fetchOne(clazz: Class<T>): T? {
        val metaData = resultSet.metaData
        if (!resultSet.next()) {
            return null
        }

        val filler = Filler.create(newInstance(clazz))

        for (i in 1..metaData.columnCount) {
            filler.set(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)))
        }
        return filler.getInstance()
    }

    inline fun <reified T> fetchAll(): List<T> {
        return fetchAll(T::class.java)
    }

    fun <T> fetchAll(clazz: Class<T>): List<T> {
        val metaData = resultSet.metaData
        val list = ArrayList<T>()
        while (resultSet.next()) {
            val filler = Filler.create(newInstance(clazz))
            for (i in 1..metaData.columnCount) {
                filler.set(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)))
            }
            list += filler.getInstance()
        }
        return list
    }

    private fun <T> newInstance(clazz: Class<T>): T {
        try {
            return clazz.newInstance()
        } catch (e: InstantiationException) {
            throw io.github.zhaofanzhe.kom.KomException(
                """No default constructor was found in class ${clazz.typeName}""",
                e
            )
        }
    }

}