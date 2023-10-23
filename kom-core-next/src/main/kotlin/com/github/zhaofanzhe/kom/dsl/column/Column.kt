package com.github.zhaofanzhe.kom.dsl.column

import com.github.zhaofanzhe.kom.dsl.express.Express
import com.github.zhaofanzhe.kom.dsl.selectable.Selectable
import com.github.zhaofanzhe.kom.dsl.table.Table
import com.github.zhaofanzhe.kom.dsl.toolkit.Bundle

class Column<R>(
    internal val table: Table,
    internal val name: String,
    internal val type: String,
    internal var isPrimaryKey: Boolean = false,
    internal var isAutoIncrement: Boolean = false,
    internal var indexName: String? = null,
    internal var nullable: Boolean = false,
    internal var unique: Boolean = false,
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

fun <R> Column<R>.primaryKey(): Column<R> {
    if (this.isPrimaryKey) return this
    this.isPrimaryKey = true
    this.table.primaryKey += this
    return this
}

fun <R> Column<R>.autoIncrement(): Column<R> {
    if (this.isAutoIncrement) return this
    this.isAutoIncrement = true
    return this
}

fun <R> Column<R>.index(indexName: String = this.name): Column<R> {
    if (this.indexName != null) return this
    this.indexName = indexName
    val columns =
        this.table.indexes[indexName] ?: mutableListOf<Column<*>>().also { this.table.indexes += (indexName to it) }
    columns += this
    return this
}

fun <R> Column<R>.nullable(): Column<R> {
    this.nullable = true
    return this
}

fun <R> Column<R>.unique(): Column<R> {
    this.unique = true
    return this
}

fun <R> Column<R>.comment(comment: String): Column<R> {
    this.comment = comment
    return this
}