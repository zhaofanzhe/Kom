package com.github.zhaofanzhe.kom.generator

import com.github.zhaofanzhe.kom.expression.*
import com.github.zhaofanzhe.kom.generator.provider.GeneratorProvider
import com.github.zhaofanzhe.kom.utils.SqlBuilder


abstract class SqlGenerator<T : SqlExpression> {

    companion object {

        fun dispatch(expression: SqlExpression): SqlBuilder {
            val builder = SqlBuilder()
            dispatch(builder, expression)
            return builder
        }

        fun dispatch(builder: SqlBuilder, expression: SqlExpression) {
            val generator = GeneratorProvider[expression::class]
                ?: throw IllegalArgumentException("No generator for $expression")
            generator.generate(builder, expression)
        }

    }

    abstract fun generate(builder: SqlBuilder, expression: T)

}

