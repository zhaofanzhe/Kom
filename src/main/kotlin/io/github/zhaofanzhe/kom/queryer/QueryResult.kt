@file:Suppress("DuplicatedCode")

package io.github.zhaofanzhe.kom.queryer

import io.github.zhaofanzhe.kom.KomException
import io.github.zhaofanzhe.kom.queryer.filler.Filler
import java.sql.ResultSet
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class QueryResult(private val resultSet: ResultSet) {

    inline fun <reified T : Any> fetchOne(): T? {
        return fetchOne(T::class)
    }

    fun <T : Any> fetchOne(kClass: KClass<T>): T? {
        val metaData = resultSet.metaData
        if (!resultSet.next()) {
            return null
        }

        val filler = Filler.create(newInstance(kClass))

        for (i in 1..metaData.columnCount) {
            filler.set(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)))
        }
        return filler.getInstance()
    }

    inline fun <reified T : Any> fetchAll(): List<T> {
        return fetchAll(T::class)
    }

    fun <T : Any> fetchAll(kClass: KClass<T>): List<T> {
        val metaData = resultSet.metaData
        val list = ArrayList<T>()
        while (resultSet.next()) {
            val filler = Filler.create(newInstance(kClass))
            for (i in 1..metaData.columnCount) {
                filler.set(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)))
            }
            list += filler.getInstance()
        }
        return list
    }

    private fun <T : Any> newInstance(kClass: KClass<T>): T {
        val ctor = kClass.primaryConstructor
        if (ctor != null && ctor.parameters.isEmpty()) {
            return ctor.call()
        } else {
            throw KomException(
                """No default constructor was found in class ${kClass.qualifiedName}"""
            )
        }
    }

}