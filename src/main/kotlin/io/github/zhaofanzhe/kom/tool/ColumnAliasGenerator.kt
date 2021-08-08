package io.github.zhaofanzhe.kom.tool

import io.github.zhaofanzhe.kom.express.Column
import io.github.zhaofanzhe.kom.express.Table
import io.github.zhaofanzhe.kom.express.declare.Declare
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class ColumnAliasGenerator(private val tableAliasGenerator: TableAliasGenerator) {

    internal val columns = mutableMapOf<Table<*>, MutableMap<Column<*>, String>>()

    internal val others = mutableMapOf<Declare<*>, String>()

    internal val othersGenerator = AliasGenerator()

    fun next(column: Column<*>): String {
        val table = column.table()
        return columns.getOrPut(table) {
            mutableMapOf()
        }.getOrPut(column) {
            """${tableAliasGenerator.next(table)}__${column.columnName()}"""
        }
    }

    fun next(other: Declare<*>): String {
        return others.getOrPut(other) {
            othersGenerator.next("__col")
        }
    }

}