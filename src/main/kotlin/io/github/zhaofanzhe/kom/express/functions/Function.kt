package io.github.zhaofanzhe.kom.express.functions

import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

@Suppress("MemberVisibilityCanBePrivate")
class Function<T>(init: (Function<T>) -> Unit) : Declare<T> {

    init {
        init(this)
    }

    internal lateinit var declare: DeclareExpress

    internal lateinit var func: FunctionExpress

    override fun declare(): DeclareExpress {
        return declare
    }

    fun func(): FunctionExpress {
        return func
    }

    override fun toString(): String {
        return func.express()
    }

}