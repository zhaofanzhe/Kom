package io.github.zhaofanzhe.kom.tool

import io.github.zhaofanzhe.kom.express.Table
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class TableAliasGenerator {

    internal val generators = mutableMapOf<KClass<out Table<*>>, AliasGenerator>()

    internal val tables = mutableMapOf<Table<*>, String>()

    fun next(table: Table<*>): String {
        return tables.getOrPut(table) {
            generators.getOrPut(table::class) {
                AliasGenerator()
            }.next(table.tableName())
        }
    }

}