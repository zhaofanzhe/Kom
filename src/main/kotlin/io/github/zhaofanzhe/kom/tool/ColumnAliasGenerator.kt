package io.github.zhaofanzhe.kom.tool

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Table
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class ColumnAliasGenerator(private val tableAliasGenerator: TableAliasGenerator) {

    internal val columns = mutableMapOf<Table<*>, MutableMap<Column<*>, String>>()

    fun next(column: Column<*>): String {
        val table = column.table()
        return columns.getOrPut(table) {
            mutableMapOf()
        }.getOrPut(column) {
            """${tableAliasGenerator.next(table)}__${column.columnName()}"""
        }
    }

}