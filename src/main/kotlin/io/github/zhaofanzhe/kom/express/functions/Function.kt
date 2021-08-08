package io.github.zhaofanzhe.kom.express.functions

import io.github.zhaofanzhe.kom.express.ITable
import io.github.zhaofanzhe.kom.express.declare.Declare
import io.github.zhaofanzhe.kom.express.declare.DeclareExpress

@Suppress("MemberVisibilityCanBePrivate")
class Function<T>(
    private val prototype: Declare<T>? = null,
    private val init: (Function<T>) -> Unit,
) : Declare<T> {

    init {
        init(this)
    }

    internal lateinit var declareExpress: DeclareExpress

    internal lateinit var functionExpress: FunctionExpress

    override fun declareExpress(): DeclareExpress {
        return declareExpress
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
        return Function(prototype = this, init = init)
    }

    fun func(): FunctionExpress {
        return functionExpress
    }

    override fun toString(): String {
        return functionExpress.express()
    }

}