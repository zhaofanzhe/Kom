package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress
import kotlin.reflect.KClass

interface ITable<T : Any> {

    fun declares(): List<Declare<*>>

    fun declareExpress(): Array<DeclareExpress>

    fun tableName(): String

    fun entityClass(): KClass<T>

    fun source(): Any?

}