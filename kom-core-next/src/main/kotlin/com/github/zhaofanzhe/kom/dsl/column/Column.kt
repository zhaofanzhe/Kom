package com.github.zhaofanzhe.kom.dsl.column

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.structure.ColumnStructure

class Column<R>(
    internal val table: Table,
    internal val name: String,
    internal val type: String,
    internal var isAutoIncrement: Boolean = false,
    internal var isNullable: Boolean = false,
    internal var comment: String? = null,
) : Express<R>, Selectable {

    private fun fullColumnName(): String {
        return "${this.table.tableName()}.`${this.name}`"
    }

    override fun generateExpress(): Bundle {
        return Bundle(sql = fullColumnName())
    }

    override fun generateSelectable(): Bundle {
        return generateExpress()
    }

    override fun flatName(): String = this.name

    override fun toString(): String {
        return fullColumnName()
    }

}

fun <R> Column<R>.autoIncrement(): Column<R> {
    if (this.isAutoIncrement) return this
    this.isAutoIncrement = true
    return this
}

fun <R> Column<R>.nullable(): Column<R> {
    this.isNullable = true
    return this
}

fun <R> Column<R>.comment(comment: String): Column<R> {
    this.comment = comment
    return this
}

fun <R> Column<R>.primaryKey(): Column<R> {
    this.table.primaryKey += this
    return this
}

fun <R> Column<R>.index(indexName: String = this.name): Column<R> {
    val finalIndexName = "${table.name}_${indexName}_index"
    this.table.indexes.getOrPut(finalIndexName) { mutableSetOf() } += this
    return this
}

fun <R> Column<R>.unique(indexName: String = this.name): Column<R> {
    val finalIndexName = "${table.name}_${indexName}_uindex"
    this.table.uniqueIndexes.getOrPut(finalIndexName) { mutableSetOf() } += this
    return this
}

fun Column<*>.toStructure(): ColumnStructure {
    return ColumnStructure(
        name = this.name,
        type = this.type,
        isAutoIncrement = this.isAutoIncrement,
        isNullable = this.isNullable,
        comment = this.comment,
    )
}