package io.github.zhaofanzhe.kom.express.declare

import io.github.zhaofanzhe.kom.express.ITable

interface Declare<T> {

    fun declareExpress(): DeclareExpress

    fun isPrototypeMatch(declare: Declare<T>): Boolean

    fun prototype(): Declare<T>?

    fun clone(table: ITable<*>): Declare<T>

}