package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.Bundle

class ParamExpress<R>(
    val value: R
) : Express<R> {

    override fun generateExpress(): Bundle {
        return Bundle("?", listOf(this.value))
    }

}

val <R> R.param
    get() = ParamExpress(this)