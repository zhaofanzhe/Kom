package com.github.zhaofanzhe.kom.expression

import com.github.zhaofanzhe.kom.utils.SqlBuilder

abstract class SqlGenerator<T : SqlExpression> {

    companion object;

    abstract fun generate(builder: SqlBuilder, expression: T)

}

fun SqlGenerator.Companion.generate(expression: SqlExpression): SqlBuilder {
    val builder = SqlBuilder()
    generate(builder, expression)
    return builder
}

fun SqlGenerator.Companion.generate(builder: SqlBuilder, expression: SqlExpression) {
    when (expression) {
        is SelectExpression -> SelectGenerator().generate(builder, expression)
        is ArgumentExpression -> ArgumentGenerator().generate(builder, expression)
        is Comparison<*> -> ComparisonGenerator().generate(builder, expression)
        is LogicalExpression -> LogicalGenerator().generate(builder, expression)
    }
}

class SelectGenerator : SqlGenerator<SelectExpression>() {

    override fun generate(builder: SqlBuilder, expression: SelectExpression) {
        builder.writeKeyword("SELECT ${expression.columns!!.joinToString(separator = ", ") { it.columnName }}")
        builder.writeKeyword(" FROM ${expression.table.tableName}")
        expression.where?.let {
            builder.writeKeyword(" WHERE ")
            SqlGenerator.generate(builder, it)
        }
    }

}

class ArgumentGenerator : SqlGenerator<ArgumentExpression>() {

    override fun generate(builder: SqlBuilder, expression: ArgumentExpression) {
        builder.writeKeyword("?")
        builder.writeParameters(expression.value)
    }

}

class ComparisonGenerator : SqlGenerator<Comparison<*>>() {

    override fun generate(builder: SqlBuilder, expression: Comparison<*>) {
        builder.writeKeyword("${expression.column.columnName} ${expression.operator} ${expression.value}")
    }

}

class LogicalGenerator : SqlGenerator<LogicalExpression>() {

    override fun generate(builder: SqlBuilder, expression: LogicalExpression) {
        SqlGenerator.generate(builder, expression.left)
        builder.writeKeyword(expression.operator)
        SqlGenerator.generate(builder, expression.right)
    }

}