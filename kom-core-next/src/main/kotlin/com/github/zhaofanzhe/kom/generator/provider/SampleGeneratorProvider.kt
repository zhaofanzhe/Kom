package com.github.zhaofanzhe.kom.generator.provider

import com.github.zhaofanzhe.kom.expression.SqlExpression
import com.github.zhaofanzhe.kom.generator.SqlGenerator
import kotlin.reflect.KClass

abstract class SampleGeneratorProvider : GeneratorProvider {

    private val generators = mutableMapOf<KClass<*>, SqlGenerator<*>>()

    fun <ST : SqlExpression, SG : SqlGenerator<ST>> register(clazz: KClass<ST>, generator: SG) {
        generators[clazz] = generator
    }

    @Suppress("UNCHECKED_CAST")
    override fun <SE : SqlExpression, SG : SqlGenerator<SE>> get(clazz: KClass<SE>): SG? {
        return generators[clazz] as SG?
    }

}