package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.ColumnDeclareExpress
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

@Suppress("MemberVisibilityCanBePrivate")
data class Column<T>(
    private val table: ITable<*>,
    private val columnName: String,
    private val fieldName: String,
    private val prototype: Declare<T>? = null,
) : Declare<T> {

    internal fun columnName(): String {
        return columnName
    }

    internal fun fieldName(): String {
        return fieldName
    }

    internal fun table(): ITable<*> {
        return table
    }

    internal fun columnExpress(): ColumnExpress {
        return ColumnExpress(this)
    }

    override fun declareExpress(): DeclareExpress {
        return ColumnDeclareExpress(this)
    }

    override fun isPrototypeMatch(declare: Declare<T>): Boolean {
        if (this.prototype == null) return false
        if (this.prototype == declare) return true
        return this.prototype.isPrototypeMatch(declare)
    }

    override fun prototype(): Declare<T>? {
        return prototype
    }

    override fun clone(table: ITable<*>): Declare<T> {
        return Column(table, columnName, fieldName, this)
    }

    override fun toString(): String {
        return columnName
    }

}