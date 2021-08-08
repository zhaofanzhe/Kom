package io.github.zhaofanzhe.kom.tool

import io.github.zhaofanzhe.kom.express.ITable
import io.github.zhaofanzhe.kom.express.Table
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class TableAliasGenerator {

    internal val generators = mutableMapOf<KClass<out ITable<*>>, AliasGenerator>()

    internal val tables = mutableMapOf<ITable<*>, String>()

    fun next(table: ITable<*>): String {
        return tables.getOrPut(table) {
            generators.getOrPut(table::class) {
                AliasGenerator()
            }.next(table.tableName())
        }
    }

}