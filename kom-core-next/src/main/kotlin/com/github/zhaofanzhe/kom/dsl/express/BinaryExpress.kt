package com.github.zhaofanzhe.kom.dsl.express

import com.github.zhaofanzhe.kom.dsl.Bundle
import com.github.zhaofanzhe.kom.dsl.core.Express
import com.github.zhaofanzhe.kom.dsl.mergeBundles

class BinaryExpress<R>(
    val op: String,
    val left: Express<R>,
    val right: Express<R>,
) : Express<Boolean> {

    override fun generateExpress(): Bundle {
        return mergeBundles(left.generateExpress(), right.generateExpress(), separator = " $op ")
    }

}

infix fun <R> Express<R>.eq(other: Express<R>): Express<Boolean> {
    return BinaryExpress("=",this, other)
}

infix fun <R> Express<R>.neq(other: Express<R>): Express<Boolean> {
    return BinaryExpress("<>",this, other)
}

infix fun <R> Express<R>.gt(other: Express<R>): Express<Boolean> {
    return BinaryExpress(">",this, other)
}

infix fun <R> Express<R>.gte(other: Express<R>): Express<Boolean> {
    return BinaryExpress(">=",this, other)
}

infix fun <R> Express<R>.lt(other: Express<R>): Express<Boolean> {
    return BinaryExpress("<",this, other)
}

infix fun <R> Express<R>.lte(other: Express<R>): Express<Boolean> {
    return BinaryExpress("<=",this, other)
}

infix fun Express<String>.like(other: Express<String>): Express<Boolean> {
    return BinaryExpress("like",this, other)
}