package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.tool.ColumnAliasGenerator
import io.github.zhaofanzhe.kom.tool.TableAliasGenerator

@Suppress("MemberVisibilityCanBePrivate")
class Context {

    internal val tableAliasGenerator = TableAliasGenerator()

    internal val columnAliasGenerator = ColumnAliasGenerator(tableAliasGenerator)

    // root table to current table
    internal var tables = mutableMapOf<ITable<*>, ITable<*>>()

    internal fun setTables(vararg tables: ITable<*>) {
        this.tables = mutableMapOf()
        tables.forEach {
            this.tables[it.rootTable()] = it
        }
    }

}