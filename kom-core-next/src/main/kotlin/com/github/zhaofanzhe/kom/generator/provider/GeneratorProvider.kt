package com.github.zhaofanzhe.kom.generator.provider

import com.github.zhaofanzhe.kom.expression.SqlExpression
import com.github.zhaofanzhe.kom.generator.SqlGenerator
import kotlin.reflect.KClass

interface GeneratorProvider {

    companion object {

        @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
        operator fun get(clazz: KClass<out SqlExpression>): SqlGenerator<SqlExpression>? {
            return DefaultGeneratorProvider[clazz]
        }

    }

    operator fun <SE : SqlExpression, SG : SqlGenerator<SE>> get(clazz: KClass<SE>): SG?

}