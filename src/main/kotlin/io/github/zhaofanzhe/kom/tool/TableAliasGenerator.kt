package io.github.zhaofanzhe.kom.tool

import io.github.zhaofanzhe.kom.express.Table
import kotlin.reflect.KClass

class TableAliasGenerator {

    private val generators = mutableMapOf<KClass<out Table<*>>, AliasGenerator>()

    private val tables = mutableMapOf<Table<*>, String>()

    fun next(table: Table<*>): String {
        return tables.getOrPut(table) {
            generators.getOrPut(table::class) {
                AliasGenerator()
            }.next(table.tableName())
        }
    }

}