package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.table.Column
import com.github.zhaofanzhe.kom.table.Table

// 定义条件表达式的接口
sealed class Expression {
    abstract fun toSQL(): String
}

// 简单比较表达式
class Comparison<T>(private val column: Column<T>, private val operator: String, private val value: Any) :
    Expression() {
    override fun toSQL(): String {
        val formattedValue = when (value) {
            is String -> "'$value'"
            else -> value.toString()
        }
        return "${column.columnName} $operator $formattedValue"
    }
}

// 逻辑组合表达式
class LogicalExpression(private val left: Expression, private val operator: String, private val right: Expression) :
    Expression() {
    override fun toSQL(): String = "(${left.toSQL()} $operator ${right.toSQL()})"
}

infix fun <T : Any> Column<T>.eq(value: T): Comparison<T> = Comparison(this, "=", value)

infix fun <T : Any> Column<T>.ne(value: T): Comparison<T> = Comparison(this, "<>", value)

infix fun <T : Any> Column<T>.gt(value: T): Comparison<T> = Comparison(this, ">", value)

infix fun <T : Any> Column<T>.lt(value: T): Comparison<T> = Comparison(this, "<", value)

// 逻辑操作符
infix fun Expression.and(other: Expression): LogicalExpression = LogicalExpression(this, "AND", other)

infix fun Expression.or(other: Expression): LogicalExpression = LogicalExpression(this, "OR", other)



sealed class SqlExpression {
    abstract fun toSQL(): String
}

class SelectExpression : SqlExpression() {

    private lateinit var columns: List<Column<*>>

    private var table: Table? = null

    private var where: Expression? = null

    fun select(vararg columns: Column<*>): SelectExpression {
        this.columns = columns.toList()
        return this
    }

    fun from(table: Table): SelectExpression {
        this.table = table
        return this
    }

    fun where(condition: Expression): SelectExpression {
        this.where = condition
        return this
    }

    fun where(condition: () -> Expression): SelectExpression {
        this.where = condition()
        return this
    }

    override fun toSQL(): String {
        val base = "SELECT ${columns.joinToString(", ") { it.columnName }}"
        val from = table?.let { " FROM ${it.tableName}" } ?: ""
        val where = where?.let { " WHERE ${it.toSQL()}" } ?: ""
        return base + from + where
    }
}

fun select(vararg columns: Column<*>): SelectExpression {
    return SelectExpression().select(*columns)
}